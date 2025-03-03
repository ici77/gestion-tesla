const API_URL = "http://localhost:8080";

let clientes = [];
let currentPage = 1;
const rowsPerPage = 10;

// Obtener clientes desde la API
async function obtenerClientes() {
    try {
        const response = await fetch(`${API_URL}/clientes`);
        if (!response.ok) throw new Error("Error al obtener clientes");

        clientes = await response.json();
        mostrarPagina(currentPage);
    } catch (error) {
        alert("❌ Error al obtener clientes.");
        console.error("Error:", error);
    }
}

// Mostrar clientes en la tabla con paginación
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
            <td>
                <button class="btn btn-edit" onclick="editarCliente(${cliente.idCliente})">Editar</button>
                <button class="btn btn-delete" onclick="eliminarCliente(${cliente.idCliente})">Eliminar</button>
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
    if (currentPage < Math.ceil(clientes.length / rowsPerPage)) {
        currentPage++;
        mostrarPagina(currentPage);
    }
});

// Agregar o editar cliente
document.getElementById("clienteForm").addEventListener("submit", async (event) => {
    event.preventDefault();

    const idCliente = document.getElementById("idCliente").value.trim();
    const nif = document.getElementById("nif").value.trim();
    const nombre = document.getElementById("nombre").value.trim();
    const direccion = document.getElementById("direccion").value.trim();
    const ciudad = document.getElementById("ciudad").value.trim();
    const telefono = document.getElementById("telefono").value.trim();

    if (!nif || !nombre || !direccion || !ciudad || !telefono) {
        alert("⚠️ Todos los campos son obligatorios.");
        return;
    }

    const cliente = { nif, nombre, direccion, ciudad, telefono };

    try {
        let response;
        if (idCliente) {
            response = await fetch(`${API_URL}/clientes/${idCliente}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(cliente),
            });
        } else {
            response = await fetch(`${API_URL}/clientes`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(cliente),
            });
        }

        if (!response.ok) {
            let errorMessage = "❌ Error al guardar el cliente.";
            
            try {
                const errorData = await response.json();
                if (errorData.message && errorData.message.includes("NIF ya está registrado")) {
                    errorMessage = "⚠️ Error: El NIF ya está registrado.";
                }
            } catch (jsonError) {
                const errorText = await response.text();
                if (errorText.includes("NIF ya está registrado")) {
                    errorMessage = "⚠️ Error: El NIF ya está registrado.";
                }
            }

            alert(errorMessage);
            return;
        }

        alert("✅ Cliente guardado correctamente.");
        document.getElementById("clienteForm").reset();
        document.getElementById("idCliente").value = "";
        obtenerClientes();
    } catch (error) {
        alert("❌ Error al conectar con el servidor.");
        console.error("Error:", error);
    }
});

// Eliminar cliente con confirmación
async function eliminarCliente(id) {
    if (!confirm("❗ ¿Seguro que quieres eliminar este cliente?")) return;

    try {
        const response = await fetch(`${API_URL}/clientes/${id}`, {
            method: "DELETE",
        });

        if (!response.ok) throw new Error("Error al eliminar cliente");

        alert("✅ Cliente eliminado correctamente.");
        obtenerClientes();
    } catch (error) {
        alert("❌ Error al eliminar el cliente.");
        console.error("Error:", error);
    }
}

// Cargar datos en el formulario para editar cliente
function editarCliente(id) {
    const cliente = clientes.find(c => c.idCliente === id);
    if (!cliente) return;

    document.getElementById("idCliente").value = cliente.idCliente;
    document.getElementById("nif").value = cliente.nif;
    document.getElementById("nombre").value = cliente.nombre;
    document.getElementById("direccion").value = cliente.direccion;
    document.getElementById("ciudad").value = cliente.ciudad;
    document.getElementById("telefono").value = cliente.telefono;
}

// Cargar clientes al abrir la página
document.addEventListener("DOMContentLoaded", obtenerClientes);
