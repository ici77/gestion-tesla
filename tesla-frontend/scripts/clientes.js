const API_URL = "http://localhost:8080/clientes";

let clientes = [];
let currentPage = 1;
const rowsPerPage = 10;

// Función para obtener clientes de la API
async function obtenerClientes() {
    try {
        const response = await fetch(`${API_URL}/clientes`);
        if (!response.ok) {
            throw new Error("Error al obtener clientes");
        }
        clientes = await response.json();
        mostrarPagina(currentPage);
    } catch (error) {
        console.error("Error:", error);
    }
}

// Función para mostrar clientes en la tabla
function mostrarPagina(page) {
    const tableBody = document.getElementById("clientes-table");
    if (!tableBody) return;

    tableBody.innerHTML = "";

    const start = (page - 1) * rowsPerPage;
    const end = start + rowsPerPage;
    const paginatedItems = clientes.slice(start, end);

    paginatedItems.forEach(cliente => {
        const row = tableBody.insertRow();
        row.innerHTML = `
            <td>${cliente.idCliente}</td>
            <td>${cliente.nif}</td>
            <td>${cliente.nombre}</td>
            <td>${cliente.direccion}</td>
            <td>${cliente.ciudad}</td>
            <td>${cliente.telefono}</td>
        `;
    });

    document.getElementById("pageNumber").textContent = page.toString();
}

// Botones para paginación
document.getElementById("prevPage").addEventListener("click", () => {
    if (currentPage > 1) {
        currentPage--;
        mostrarPagina(currentPage);
    }
});

document.getElementById("nextPage").addEventListener("click", () => {
    if (currentPage < Math.ceil(clientes.length / rowsPerPage)) {
        currentPage++;
        mostrarPagina(currentPage);
    }
});

// Cargar clientes al abrir la página
document.addEventListener("DOMContentLoaded", obtenerClientes);
