function stringToColor(str){
    let hash = 0;
    for ( let i = 0; i < str.length; i++) {
        hash = str.charCodeAt(i) + ((hash << 5) - hash);
    }
    const hue = hash % 360;
    return `hsla(${hue}, 65%, 60%, 0.2)`;
}

document.getElementById('btn-translate').addEventListener('click', () => {
    const textareas = document.querySelectorAll('.lang-textarea');
    let sourceLang = null;
    let sourceText = null;

    // ищем язык, для которого написан текст:
    for (const t of textareas) {
        if (t.value.trim() !== '') {
            sourceLang = t.dataset.lang;
            sourceText = t.value.trim();
            break;
        }
    }
    if (!sourceText) return;

    const targetLangs = Array.from(textareas)
        .map(t => t.dataset.lang)
        .filter(lang => lang !== sourceLang);

    fetch('/admin/translate', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [document.querySelector('meta[name="_csrf_header"]').content]:
                document.querySelector('meta[name="_csrf"]').content
        },
        body: JSON.stringify({ text: sourceText, sourceLang: sourceLang, targetLangs: targetLangs})
    })
        .then(response => response.json())
        .then(results => renderTranslationOptions(results));
});

function renderTranslationOptions(results) {
    for (const lang in results) {
        const container = document.getElementById('options-' + lang);
        container.innerHTML = '';

        const providers = results[lang];
        const names = Object.keys(providers);

        names.forEach((providerName, index) => {
            const text = providers[providerName];
            const color = stringToColor(providerName);

            const label = document.createElement('label');
            label.style.display = 'flex';
            label.style.alignItems = 'center';
            label.style.marginTop = '4px';
            label.style.textTransform = 'none';

            const radio = document.createElement('input');
            radio.type = 'radio';
            radio.name = 'translation-' + lang;
            radio.value = text;
            radio.style.flexShrink = '0';
            radio.style.width = '16px';
            radio.style.height = '16px';
            radio.style.margin = '0';
            if (names.length === 1) radio.checked = true;

            radio.addEventListener('change', () => {
                const textareaEl = document.getElementById('text-' + lang);
                textareaEl.value = text;
                textareaEl.dispatchEvent(new Event('input'));
            });

            const providerLabel = document.createElement('span');
            providerLabel.className = 'provider-label';
            providerLabel.textContent = providerName;
            providerLabel.style.backgroundColor = color;
            providerLabel.style.padding = '2px 6px';
            providerLabel.style.borderRadius = '4px';
            providerLabel.style.marginLeft = '6px';
            providerLabel.style.marginRight = '6px';

            label.appendChild(radio);
            label.appendChild(providerLabel);
            label.appendChild(document.createTextNode(text));
            container.appendChild(label);

            if (names.length === 1) {
                const textareaEl = document.getElementById('text-' + lang);
                textareaEl.value = text;
                textareaEl.dispatchEvent(new Event('input'));
            }
        });
    }
}