const API_URL = "http://localhost:8080";

let coches = [];
let currentPage = 1;
const rowsPerPage = 10;

// Obtener coches desde la API
async function obtenerCoches() {
    try {
        const response = await fetch(`${API_URL}/coches`);
        if (!response.ok) throw new Error("Error al obtener coches");

        coches = await response.json();
        mostrarPagina(currentPage);
    } catch (error) {
        console.error("Error:", error);
    }
}

// Mostrar coches en la tabla con paginación
function mostrarPagina(page) {
    const tableBody = document.getElementById("coches-table");
    if (!tableBody) return;

    tableBody.innerHTML = "";

    const start = (page - 1) * rowsPerPage;
    const end = start + rowsPerPage;
    const paginatedItems = coches.slice(start, end);

    paginatedItems.forEach(coche => {
        const row = tableBody.insertRow();
        row.innerHTML = `
            <td>${coche.idCoche}</td>
            <td>${coche.matricula}</td>
            <td>${coche.marca}</td>
            <td>${coche.modelo}</td>
            <td>${coche.color}</td>
            <td>${coche.precio} €</td>
            <td>
                <button class="btn btn-edit" onclick="editarCoche(${coche.idCoche})">Editar</button>
                <button class="btn btn-delete" onclick="eliminarCoche(${coche.idCoche})">Eliminar</button>
            </td>
        `;
    });

    document.getElementById("pageNumber").textContent = page.toString();
}

// Botones de paginación
document.getElementById("prevPage").addEventListener("click", () => {
    if (currentPage > 1) {
        currentPage--;
        mostrarPagina(currentPage);
    }
});

document.getElementById("nextPage").addEventListener("click", () => {
    if (currentPage < Math.ceil(coches.length / rowsPerPage)) {
        currentPage++;
        mostrarPagina(currentPage);
    }
});

// Agregar o editar coche
document.getElementById("cocheForm").addEventListener("submit", async (event) => {
    event.preventDefault();

    const idCoche = document.getElementById("idCoche").value;
    const matricula = document.getElementById("matricula").value;
    const marca = document.getElementById("marca").value;
    const modelo = document.getElementById("modelo").value;
    const color = document.getElementById("color").value;
    const precio = document.getElementById("precio").value;

    const coche = { matricula, marca, modelo, color, precio };

    try {
        let respuesta;
        if (idCoche) {
            respuesta = await fetch(`${API_URL}/coches/${idCoche}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(coche),
            });
        } else {
            respuesta = await fetch(`${API_URL}/coches`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(coche),
            });
        }

        if (!respuesta.ok) throw new Error("Error al guardar coche");

        alert("Coche guardado correctamente");
        document.getElementById("cocheForm").reset();
        document.getElementById("idCoche").value = "";
        obtenerCoches();
    } catch (error) {
        console.error("Error:", error);
    }
});

// Eliminar coche
async function eliminarCoche(id) {
    if (!confirm("¿Seguro que quieres eliminar este coche?")) return;

    try {
        const respuesta = await fetch(`${API_URL}/coches/${id}`, {
            method: "DELETE",
        });

        if (!respuesta.ok) throw new Error("Error al eliminar coche");

        alert("Coche eliminado correctamente");
        obtenerCoches();
    } catch (error) {
        console.error("Error:", error);
    }
}

// Cargar datos en el formulario para editar coche
function editarCoche(id) {
    const coche = coches.find(c => c.idCoche === id);
    if (!coche) return;

    document.getElementById("idCoche").value = coche.idCoche;
    document.getElementById("matricula").value = coche.matricula;
    document.getElementById("marca").value = coche.marca;
    document.getElementById("modelo").value = coche.modelo;
    document.getElementById("color").value = coche.color;
    document.getElementById("precio").value = coche.precio;
}

// Cargar coches al abrir la página
document.addEventListener("DOMContentLoaded", obtenerCoches);
