
var estadosPQR;
var listaPQRs;


function cambiarEstadoPQR(event, pos) {
    let estadoPQR = event.target.value;
    let ajaxCall = new XMLHttpRequest();
    ajaxCall.open('PUT', './AdministrarPQRs', true);
    ajaxCall.setRequestHeader("Content-type", "application/json;charset=UTF-8");
    ajaxCall.onreadystatechange = myCallBack;

    let data = {'estadoPQR': estadoPQR, 'numeroRadicado': pos};
    ajaxCall.send(JSON.stringify(data));
}
;

function myCallBack() {
    if (this.readyState === 4 && this.status === 200) {
        alert("Se ha actualizado los datos correctamente");
    } else if (this.readyState === 4) {
        alert("No se ha logrado actualizar correctamente los datos");
    }
}

function myCallBack2() {
    if (this.readyState === 4 && this.status === 200) {
        listaPQRs = JSON.parse(this.responseText);
        console.log(listaPQRs);

    }
}

function obtenerLista() {
    let ajaxCall = new XMLHttpRequest();
    ajaxCall.open('GET', './AdministrarPQRs', false);
    ajaxCall.onreadystatechange = myCallBack2;
    ajaxCall.send();
}


function ajaxBorrarPQR(posicion) {
    let ajaxCall = new XMLHttpRequest();
    ajaxCall.open('DELETE', './AdministrarPQRs', true);
    ajaxCall.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
    ajaxCall.onreadystatechange = myCallBack;

    ajaxCall.send(posicion);
}

function borrarPQR() {
    let posPQR = document.getElementById("eliminarPQR").value;
    
    if (Number.isInteger(+posPQR) && posPQR <= (listaPQRs.length - 1) &&
            posPQR >= 0) {
        ajaxBorrarPQR(posPQR);
        location.reload();
    } else {
        alert("Por favor, ingresar unicamente numeros enteros correspondientes a un numero de radicado de las PQRs");
    }
}

window.onload = function () {

    obtenerLista();
    let tCuerpo = document.getElementById("cuerpoTabla");

    let valores = Object.keys(listaPQRs[0]);


    for (let i = 0; i < listaPQRs.length; i++) {
        let fila = document.createElement("tr");
        let selectEstados = document.createElement("select");
        selectEstados.setAttribute("class", "estadoPQR");
        selectEstados.setAttribute("id", "estadoPQR_" + i);

        for (let j = 0; j < valores.length; j++) {
            let celda = document.createElement("td");
            let contenidoCelda;

            if (valores[j] === "estadoPQR") {

                contenidoCelda = selectEstados;
                let opcionesEstados = ['recibido', 'en atencion', 'resuelto'];
                let temp;
                switch (listaPQRs[i][valores[j]]) {
                    case opcionesEstados[0]:
                        break;
                    case opcionesEstados[1]:
                        temp = opcionesEstados[0];
                        opcionesEstados[0] = opcionesEstados[1];
                        opcionesEstados[1] = opcionesEstados[2];
                        opcionesEstados[2] = temp;
                        break;
                    case opcionesEstados[2]:
                        temp = opcionesEstados[0];
                        opcionesEstados[0] = opcionesEstados[2];
                        opcionesEstados[2] = temp;
                        break;
                    default:
                        console.log("ERROR, ESTADO NO ENCONTRADO");
                }
                for (let i = 0; i < opcionesEstados.length; i++) {
                    let opcion = document.createElement("option");
                    opcion.value = opcionesEstados[i];
                    opcion.text = opcionesEstados[i];
                    contenidoCelda.appendChild(opcion);
                }

            } else {
                contenidoCelda = document.createTextNode(listaPQRs[i][valores[j]]);
            }
            celda.appendChild(contenidoCelda);
            fila.appendChild(celda);
        }
        tCuerpo.appendChild(fila);
    }


    estadosPQR = document.getElementsByClassName("estadoPQR");
    for (let i = 0; i < estadosPQR.length; i++) {
        estadosPQR[i].addEventListener('change', function () {
            cambiarEstadoPQR(event, i);
        });
    }

   

};





