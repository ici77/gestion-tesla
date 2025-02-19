const API_URL = "http://localhost:8080";

let revisiones = [];
let coches = [];
let currentPageRevisiones = 1;
const rowsPerPageRevisiones = 10;

async function cargarCoches() {
    try {
        const response = await fetch(`${API_URL}/coches`);
        if (!response.ok) throw new Error("Error al obtener coches");

        coches = await response.json();
        console.log("Coches recibidos:", coches);

        const cocheSelect = document.getElementById("coche");
        cocheSelect.innerHTML = "<option value='' disabled selected>Seleccione un coche</option>";

        coches.forEach(coche => {
            let option = document.createElement("option");
            option.value = coche.idCoche;
            option.textContent = `${coche.marca} - ${coche.modelo}`;
            cocheSelect.appendChild(option);
        });
    } catch (error) {
        console.error("Error:", error);
    }
}

async function obtenerRevisiones() {
    try {
        const response = await fetch(`${API_URL}/revisiones`);
        if (!response.ok) throw new Error("Error al obtener revisiones");

        revisiones = await response.json();
        console.log("Revisiones recibidas:", revisiones);
        mostrarPaginaRevisiones(currentPageRevisiones);
    } catch (error) {
        console.error("Error:", error);
    }
}

function mostrarPaginaRevisiones(page) {
    const tableBody = document.getElementById("revisiones-table");
    if (!tableBody) return;

    tableBody.innerHTML = "";
    const start = (page - 1) * rowsPerPageRevisiones;
    const end = start + rowsPerPageRevisiones;
    const paginatedItems = revisiones.slice(start, end);

    paginatedItems.forEach(revision => {
        const idRevision = revision.idRevision;
        const fecha = revision.fecha || "Sin fecha";
        const cocheInfo = revision.coche ? `${revision.coche.marca} - ${revision.coche.modelo}` : "Sin coche";

        const filtro = revision.cambioFiltro ? "Sí" : "No";
        const aceite = revision.cambioAceite ? "Sí" : "No";
        const frenos = revision.cambioFrenos ? "Sí" : "No";

        const row = tableBody.insertRow();
        row.innerHTML = `
            <td>${idRevision}</td>
            <td>${fecha}</td>
            <td>${cocheInfo}</td>
            <td>${filtro}</td>
            <td>${aceite}</td>
            <td>${frenos}</td>
            <td>${revision.observaciones || "Sin observaciones"}</td>
            <td>
                <button class="btn btn-edit" onclick="editarRevision(${idRevision})">Editar</button>
                <button class="btn btn-delete" onclick="eliminarRevision(${idRevision})">Eliminar</button>
            </td>
        `;
    });

    document.getElementById("pageNumberRevisiones").textContent = page.toString();
}

async function guardarRevision(event) {
    event.preventDefault();

    const idRevision = document.getElementById("idRevision").value;
    const idCoche = document.getElementById("coche").value;
    const cambioFiltro = document.getElementById("filtro").checked;
    const cambioAceite = document.getElementById("aceite").checked;
    const cambioFrenos = document.getElementById("frenos").checked;
    const observaciones = document.getElementById("observaciones").value;
    const fecha = document.getElementById("fecha").value || null;

    if (!idCoche) {
        alert("Debe seleccionar un coche.");
        return;
    }

    let revision = { 
        coche: { idCoche: parseInt(idCoche) },
        cambioFiltro,
        cambioAceite,
        cambioFrenos,
        observaciones,
        fecha
    };

    if (idRevision) {
        revision.idRevision = parseInt(idRevision);
    }

    console.log("Enviando revisión:", revision);

    try {
        let respuesta;
        if (idRevision) {
            respuesta = await fetch(`${API_URL}/revisiones/${idRevision}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(revision),
            });
        } else {
            respuesta = await fetch(`${API_URL}/revisiones`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(revision),
            });
        }

        if (!respuesta.ok) throw new Error("Error al guardar revisión");

        alert("Revisión guardada correctamente");
        document.getElementById("revisionForm").reset();
        document.getElementById("idRevision").value = ""; 
        await obtenerRevisiones();
    } catch (error) {
        console.error("Error:", error);
    }
}

function editarRevision(id) {
    const revision = revisiones.find(r => r.idRevision == id);
    if (!revision) {
        console.error("Revisión no encontrada para edición:", id);
        return;
    }

    console.log("Editando revisión:", revision);

    document.getElementById("idRevision").value = revision.idRevision;
    document.getElementById("coche").value = revision.coche ? revision.coche.idCoche : "";
    document.getElementById("filtro").checked = revision.cambioFiltro;
    document.getElementById("aceite").checked = revision.cambioAceite;
    document.getElementById("frenos").checked = revision.cambioFrenos;
    document.getElementById("observaciones").value = revision.observaciones || "";
    document.getElementById("fecha").value = revision.fecha ? revision.fecha : "";
}

async function eliminarRevision(id) {
    if (!confirm("¿Seguro que quieres eliminar esta revisión?")) return;

    try {
        const respuesta = await fetch(`${API_URL}/revisiones/${id}`, { method: "DELETE" });

        if (!respuesta.ok) throw new Error("Error al eliminar revisión");

        alert("Revisión eliminada correctamente");
        await obtenerRevisiones();
    } catch (error) {
        console.error("Error:", error);
    }
}

document.addEventListener("DOMContentLoaded", () => {
    cargarCoches();
    obtenerRevisiones();
});

document.getElementById("revisionForm").addEventListener("submit", guardarRevision);
