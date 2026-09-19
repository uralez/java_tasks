let currentDeleteUrl = null;

function openConfirm(title, text, deleteUrl, contextTopic = '', contextQuestion = '') {
    document.getElementById('confirm-title').textContent = title;
    document.getElementById('confirm-text').textContent = text;
    document.getElementById('confirm-topic').textContent = contextTopic;
    document.getElementById('confirm-question').textContent = contextQuestion;
    currentDeleteUrl = deleteUrl;
    document.getElementById('confirm-overlay').style.display = 'flex';
}

function closeConfirm() {
    document.getElementById('confirm-overlay').style.display = 'none';
}

function submitDelete() {
    if (!currentDeleteUrl) return;
    const token = document.querySelector('meta[name="_csrf"]').content;
    const header = document.querySelector('meta[name="_csrf_header"]').content;
    fetch(currentDeleteUrl, {
        method: 'POST',
        headers: { [header]: token }
    }).then(response => {
        window.location.href = response.url;
    });
}

function handleOverlayClick(e) {
    if (e.target === document.getElementById('confirm-overlay')) closeConfirm();
}
