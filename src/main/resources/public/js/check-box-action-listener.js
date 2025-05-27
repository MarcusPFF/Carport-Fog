document.addEventListener("DOMContentLoaded", function () {
    const checkbox = document.getElementById("redskabsrumCheckbox");
    const options = document.getElementById("redskabsrumOptions");
    const lengthSelect = document.getElementById("redskabsrumLength");
    const widthSelect = document.getElementById("redskabsrumWidth");

    function updateShedOptionsDisplay() {
        if (checkbox.checked) {
            options.style.display = "block";
        } else {
            options.style.display = "none";
            // Clear selections when unchecked
            if (lengthSelect) lengthSelect.value = "";
            if (widthSelect) widthSelect.value = "";
        }
    }

    if (checkbox && options) {
        checkbox.addEventListener("change", updateShedOptionsDisplay);
        updateShedOptionsDisplay(); // Initial visning baseret på om boksen var tjekket
    }
});
