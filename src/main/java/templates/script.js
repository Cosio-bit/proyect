
    let tiempoTotalSegundos = 6 * 3600; // 6 horas
    let interval;
    let tiempoInicial = tiempoTotalSegundos;
    let proyectorDisponible = true;
    let horaPrestamo;

    document.getElementById("startButton").addEventListener("click", function() {
    if (proyectorDisponible) {
    this.disabled = true; // Deshabilita el botón de "Pedir"
    document.getElementById("returnButton").disabled = false; // Habilita el botón de "Devolver"
    document.getElementById("damageReport").style.display = "block"; // Muestra opciones de daño
    iniciarContador();
    proyectorDisponible = false;
    //obtener la hora en que se pidio el proyector
    horaPrestamo = new Date().toLocaleTimeString();
}
});

    document.getElementById("returnButton").addEventListener("click", function() {
    // Suponiendo que cada botón de devolución está en una fila diferente de la tabla
    const fila = this.closest('tr');

    // Deshabilitar botón y manejar el contador
    this.disabled = true;
    clearInterval(interval);
    registrarTiempoUsado(fila);

    // Recolectar información del proyector
    const idProyector = fila.querySelector('.idProyector').textContent;
    const idProfesor = fila.querySelector('.profesorSelector').value;
    const danado = fila.querySelector('input[name="damage"]:checked').value;
    const atrasado = fila.querySelector('.timer').textContent === "Atrasado";
    const horaDevolucion = new Date().toLocaleTimeString();


    // Enviar datos al servidor
    enviarDatosPrestamo(horaPrestamo, horaDevolucion, atrasado, horaDevolucion, idProyector, idProfesor);

    // Actualizar UI según si el proyector está dañado
    if (danado === "no") {
    proyectorDisponible = true;
    document.getElementById("startButton").disabled = false;
} else {
    document.getElementById("repairButton").style.display = "block";
}
    document.getElementById("damageReport").style.display = "none";
});

    document.getElementById("repairButton").addEventListener("click", function() {
    proyectorDisponible = true;
    this.style.display = "none"; // Oculta el botón de "Reparado"
    document.getElementById("startButton").disabled = false; // Habilita el botón de "Pedir"
});

    document.getElementById("skipButton").addEventListener("click", function() {
    const skipTime = document.getElementById("skipTime").value;
    const [horas, minutos, segundos] = skipTime.split(":").map(Number);

    if (!isNaN(horas) && !isNaN(minutos) && !isNaN(segundos)) {
    const skipTotalSegundos = horas * 3600 + minutos * 60 + segundos;
    tiempoTotalSegundos -= skipTotalSegundos;
    actualizarTiempo();

    if (tiempoTotalSegundos < 0) {
    clearInterval(interval);
    document.getElementById("timer").innerText = "Atrasado";
    registrarTiempoUsado();
    document.getElementById("returnButton").disabled = true;
    document.getElementById("startButton").disabled = false;
    proyectorDisponible = true;
}
}
});

    function iniciarContador() {
    clearInterval(interval);
    actualizarTiempo();
    interval = setInterval(function() {
    tiempoTotalSegundos--;

    actualizarTiempo();

    if (tiempoTotalSegundos < 0) {
    clearInterval(interval);
    document.getElementById("timer").innerText = "Atrasado";
    registrarTiempoUsado();
}
}, 1000);
}

    function actualizarTiempo() {
    let horas = Math.floor(tiempoTotalSegundos / 3600);
    let minutos = Math.floor((tiempoTotalSegundos % 3600) / 60);
    let segundos = tiempoTotalSegundos % 60;

    horas = horas < 10 ? '0' + horas : horas;
    minutos = minutos < 10 ? '0' + minutos : minutos;
    segundos = segundos < 10 ? '0' + segundos : segundos;

    document.getElementById("timer").innerText = `${horas}:${minutos}:${segundos}`;
}

    function registrarTiempoUsado() {
    const tiempoUsado = tiempoInicial - tiempoTotalSegundos;
    const horas = Math.floor(tiempoUsado / 3600);
    const minutos = Math.floor((tiempoUsado % 3600) / 60);
    const segundos = tiempoUsado % 60;

    let mensaje;
    if (tiempoTotalSegundos < 0) {
    mensaje = "Atrasado";
} else {
    mensaje = `Tiempo utilizado: ${horas} horas, ${minutos} minutos y ${segundos} segundos.`;
}

    document.getElementById("resultado").innerText = mensaje;
}

    function enviarDatosPrestamo(horaPrestamo, horaDevolucion, atrasado, idProyector, idProfesor) {
    const xhr = new XMLHttpRequest();
    xhr.open("POST", "/prestamos", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(JSON.stringify({ horaPrestamo, horaDevolucion, atrasado, idProyector, idProfesor }));
}
