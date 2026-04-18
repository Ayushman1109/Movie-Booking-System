const API_BASE = 'http://localhost:8080';

/* ─── Tab Switching ──────────────────────────────────────────────────────── */

function switchTab(tab) {
    const loginForm    = document.getElementById('login-form');
    const registerForm = document.getElementById('register-form');
    const loginTab     = document.getElementById('login-tab');
    const registerTab  = document.getElementById('register-tab');

    clearErrors();

    if (tab === 'login') {
        loginForm.classList.remove('hidden');
        registerForm.classList.add('hidden');
        loginTab.classList.add('active');
        registerTab.classList.remove('active');
    } else {
        registerForm.classList.remove('hidden');
        loginForm.classList.add('hidden');
        registerTab.classList.add('active');
        loginTab.classList.remove('active');
    }
}

/* ─── Password Visibility Toggle ─────────────────────────────────────────── */

function togglePassword(inputId, btn) {
    const input = document.getElementById(inputId);
    if (input.type === 'password') {
        input.type = 'text';
        btn.textContent = 'Hide';
    } else {
        input.type = 'password';
        btn.textContent = 'Show';
    }
}

/* ─── Validation Helpers ─────────────────────────────────────────────────── */

function showFieldError(inputEl, msgId, message) {
    document.getElementById(msgId).textContent = message;
    // Mark the wrapper or input with error styling
    const wrapper = inputEl.closest('.password-wrapper') || inputEl;
    wrapper.classList.add('input-error');
}

function clearErrors() {
    document.querySelectorAll('.error-msg').forEach(el => el.textContent = '');
    document.querySelectorAll('.input-error').forEach(el => el.classList.remove('input-error'));
    ['login-error', 'register-error', 'register-success'].forEach(id => {
        const el = document.getElementById(id);
        if (el) el.classList.add('hidden');
    });
}

function isValidEmail(email) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

/* ─── Login Handler ──────────────────────────────────────────────────────── */

async function handleLogin(event) {
    event.preventDefault();
    clearErrors();

    const userNameInput = document.getElementById('login-username');
    const passwordInput = document.getElementById('login-password');
    const userName      = userNameInput.value.trim();
    const password      = passwordInput.value;

    let valid = true;

    if (!userName) {
        showFieldError(userNameInput, 'login-username-error', 'Username is required.');
        valid = false;
    }

    if (!password) {
        showFieldError(passwordInput, 'login-password-error', 'Password is required.');
        valid = false;
    }

    if (!valid) return;

    try {
        const response = await fetch(`${API_BASE}/auth/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ userName, password })
        });

        if (!response.ok) {
            const errEl = document.getElementById('login-error');
            errEl.textContent = 'Invalid username or password. Please try again.';
            errEl.classList.remove('hidden');
            return;
        }

        const data = await response.json();
        localStorage.setItem('jwtToken', data.token);
        localStorage.setItem('currentUserName', userName);
        // Decode role from JWT and redirect admin straight to admin panel
        try {
            const payload = JSON.parse(atob(data.token.split('.')[1]));
            if (payload.role === 'ADMIN') {
                window.location.href = '../html/admin/index.html';
                return;
            }
        } catch (e) { /* fall through to default redirect */ }
        window.location.href = '../index.html';
    } catch (err) {
        const errEl = document.getElementById('login-error');
        errEl.textContent = 'Could not connect to server. Please try again later.';
        errEl.classList.remove('hidden');
    }
}

/* ─── Register Handler ───────────────────────────────────────────────────── */

async function handleRegister(event) {
    event.preventDefault();
    clearErrors();

    const nameInput     = document.getElementById('reg-name');
    const emailInput    = document.getElementById('reg-email');
    const passwordInput = document.getElementById('reg-password');
    const confirmInput  = document.getElementById('reg-confirm');

    const userName = nameInput.value.trim();
    const email    = emailInput.value.trim();
    const password = passwordInput.value;
    const confirm  = confirmInput.value;

    let valid = true;

    if (!userName) {
        showFieldError(nameInput, 'reg-name-error', 'Username is required.');
        valid = false;
    }

    if (!email) {
        showFieldError(emailInput, 'reg-email-error', 'Email is required.');
        valid = false;
    } else if (!isValidEmail(email)) {
        showFieldError(emailInput, 'reg-email-error', 'Please enter a valid email address.');
        valid = false;
    }

    if (!password) {
        showFieldError(passwordInput, 'reg-password-error', 'Password is required.');
        valid = false;
    } else if (password.length < 6) {
        showFieldError(passwordInput, 'reg-password-error', 'Password must be at least 6 characters.');
        valid = false;
    }

    if (!confirm) {
        showFieldError(confirmInput, 'reg-confirm-error', 'Please confirm your password.');
        valid = false;
    } else if (password !== confirm) {
        showFieldError(confirmInput, 'reg-confirm-error', 'Passwords do not match.');
        valid = false;
    }

    if (!valid) return;

    try {
        const response = await fetch(`${API_BASE}/auth/register`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ userName, email, password })
        });

        if (!response.ok) {
            const errEl = document.getElementById('register-error');
            errEl.textContent = 'Registration failed. Username or email may already be in use.';
            errEl.classList.remove('hidden');
            return;
        }

        const successEl = document.getElementById('register-success');
        successEl.textContent = 'Account created successfully! Redirecting to login...';
        successEl.classList.remove('hidden');

        nameInput.value     = '';
        emailInput.value    = '';
        passwordInput.value = '';
        confirmInput.value  = '';

        setTimeout(() => switchTab('login'), 1500);
    } catch (err) {
        const errEl = document.getElementById('register-error');
        errEl.textContent = 'Could not connect to server. Please try again later.';
        errEl.classList.remove('hidden');
    }}