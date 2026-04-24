/**
 * Lógica para el formulario de Productos
 * Inicialización de Tom Select para marcas y categorías
 */
document.addEventListener("DOMContentLoaded", function() {
    
    // Configuración para el selector de Marca
    const selectMarca = new TomSelect("#marca", {
        create: false,
        sortField: {
            field: "text",
            direction: "asc"
        },
       
        allowEmptyOption: true,
        onFocus: function() {
            this.clearSelection(); // Opcional: limpiar selección previa para buscar de cero
            this.setTextboxValue('');
        }
    });

    // Configuración para el selector de Categoría
    const selectCategoria = new TomSelect("#categoria", {
        create: false,
        sortField: {
            field: "text",
            direction: "asc"
        },
        
        allowEmptyOption: true,
        onFocus: function() {
            this.clearSelection();
            this.setTextboxValue('');
        }
    });

    console.log("Tom Select inicializado en Productos");
});
