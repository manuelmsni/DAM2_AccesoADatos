function showHide(ShowClass, HideClass) {
    const elementsToShow = document.querySelectorAll(`.${ShowClass}`);
    const elementsToHide = document.querySelectorAll(`.${HideClass}`);
  
    elementsToShow.forEach(element => {
      element.classList.remove('hidden');
    });
  
    elementsToHide.forEach(element => {
      element.classList.add('hidden');
    });
}

function copia(texto) {
  var elementoTemporal = document.createElement("textarea");
  elementoTemporal.value = texto;
  document.body.appendChild(elementoTemporal);
  elementoTemporal.select();
  document.execCommand("copy");
  document.body.removeChild(elementoTemporal);
}

function initCopia(){
    const copyableElements = document.querySelectorAll('.copy');
    copyableElements.forEach(element => {
        element.addEventListener('click', function() {
            const textToCopy = this.innerText;
            copia(textToCopy);
        });
    });
}

function switchTheme(){
    theme = document.getElementById("theme");
    checkbox = document.getElementById("themeSwitch");
    if (!checkbox.checked) {
        theme.href = "assets/themes/dark.css";
        
    } else {
        theme.href = "assets/themes/light.css";
    }
}