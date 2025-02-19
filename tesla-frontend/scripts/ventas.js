const API_URL = "http://localhost:8080";

let ventas = [];
let clientes = [];
let coches = [];

// Obtener clientes y coches para llenar los select
async function cargarClientesYCoches() {
    try {
        const responseClientes = await fetch(`${API_URL}/clientes`);
        if (!responseClientes.ok) throw new Error("Error al obtener clientes");
        clientes = await responseClientes.json();

        const responseCoches = await fetch(`${API_URL}/coches`);
        if (!responseCoches.ok) throw new Error("Error al obtener coches");
        coches = await responseCoches.json();

        const responseVentas = await fetch(`${API_URL}/ventas`);
        if (!responseVentas.ok) throw new Error("Error al obtener ventas");
        ventas = await responseVentas.json();

        const clienteSelect = document.getElementById("cliente");
        const cocheSelect = document.getElementById("coche");

        // Limpiar selects
        clienteSelect.innerHTML = "<option value='' disabled selected>Seleccione un cliente</option>";
        cocheSelect.innerHTML = "<option value='' disabled selected>Seleccione un coche</option>";

        clientes.forEach(cliente => {
            let option = document.createElement("option");
            option.value = cliente.idCliente;
            option.textContent = `${cliente.nombre} (${cliente.nif})`;
            clienteSelect.appendChild(option);
        });

        // Filtrar coches que no están en ventas
        const cochesDisponibles = coches.filter(coche => 
            !ventas.some(venta => venta.coche.idCoche === coche.idCoche)
        );

        cochesDisponibles.forEach(coche => {
            let option = document.createElement("option");
            option.value = coche.idCoche;
            option.textContent = `${coche.marca} ${coche.modelo} - ${coche.matricula}`;
            cocheSelect.appendChild(option);
        });

    } catch (error) {
        console.error("Error:", error);
    }
}

// Obtener ventas desde la API
async function obtenerVentas() {
    try {
        const response = await fetch(`${API_URL}/ventas`);
        if (!response.ok) throw new Error("Error al obtener ventas");

        ventas = await response.json();
        mostrarVentas();
    } catch (error) {
        console.error("Error:", error);
    }
}

// Mostrar ventas en la tabla
function mostrarVentas() {
    const tableBody = document.getElementById("ventas-table");
    if (!tableBody) return;

    tableBody.innerHTML = "";

    ventas.forEach(venta => {
        const row = tableBody.insertRow();
        row.innerHTML = `
            <td>${venta.idVenta}</td>
            <td>${venta.cliente.nombre} (${venta.cliente.nif})</td>
            <td>${venta.coche.marca} ${venta.coche.modelo} - ${venta.coche.matricula}</td>
            <td>${venta.coche.precio} €</td>
            <td>
                <button class="btn btn-delete" onclick="eliminarVenta(${venta.idVenta})">Eliminar</button>
            </td>
        `;
    });

    cargarClientesYCoches(); // Actualizar los coches disponibles
}

// Agregar nueva venta
document.getElementById("ventaForm").addEventListener("submit", async (event) => {
    event.preventDefault();

    const idCliente = document.getElementById("cliente").value;
    const idCoche = document.getElementById("coche").value;

    if (!idCliente || !idCoche) {
        alert("Debe seleccionar un cliente y un coche.");
        return;
    }

    const venta = { cliente: { idCliente }, coche: { idCoche } };

    try {
        const respuesta = await fetch(`${API_URL}/ventas`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(venta),
        });

        if (!respuesta.ok) {
            alert("Error: El coche ya ha sido vendido. Seleccione otro coche.");
            return;
        }

        alert("Venta registrada correctamente");
        obtenerVentas();
    } catch (error) {
        console.error("Error:", error);
    }
});

// Eliminar venta
async function eliminarVenta(id) {
    if (!confirm("¿Seguro que quieres eliminar esta venta?")) return;

    try {
        const respuesta = await fetch(`${API_URL}/ventas/${id}`, {
            method: "DELETE",
        });

        if (!respuesta.ok) throw new Error("Error al eliminar venta");

        alert("Venta eliminada correctamente");
        obtenerVentas();
    } catch (error) {
        console.error("Error:", error);
    }
}

// Cargar datos al abrir la página
document.addEventListener("DOMContentLoaded", () => {
    obtenerVentas();
});
