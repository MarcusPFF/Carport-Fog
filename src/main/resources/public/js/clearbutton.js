//Js til at fjerne værdier i input feltet
const input = document.getElementById('offerId');
const clearButton = document.querySelector('.clear-btn');

input.addEventListener('input', () => {
    clearButton.style.visibility = input.value ? 'visible' : 'hidden';
});

clearButton.addEventListener('click', () => {
    input.value = '';
    clearButton.style.visibility = 'hidden';
    input.focus();
});

clearButton.style.visibility = 'hidden';