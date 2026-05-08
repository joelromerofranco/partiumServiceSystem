/**
 * Lógica para el formulario de Productos
 * Inicialización de Select2 y AutoNumeric con soporte para diversas monedas
 */
$(document).ready(function() {
    
    // 1. Inicializar Select2
    $('.select2').select2({
        width: '100%'
    });

    // 2. Configuración de AutoNumeric
    // Definimos opciones para diferentes monedas
    const guaraniesOptions = {
        digitGroupSeparator        : '.',
        decimalCharacter           : ',',
        decimalPlaces              : 0,
        currencySymbol             : 'Gs. ',
        currencySymbolPlacement    : 'p', // p = prefix
        unformatOnSubmit           : true,
        minimumValue               : '0',
        modifyValueOnWheel         : false,
        emptyInputBehavior         : 'null'
    };

    const dolaresOptions = {
        digitGroupSeparator        : ',',
        decimalCharacter           : '.',
        decimalPlaces              : 2,
        currencySymbol             : 'US$ ',
        currencySymbolPlacement    : 'p',
        unformatOnSubmit           : true,
        minimumValue               : '0',
        modifyValueOnWheel         : false,
        emptyInputBehavior         : 'null'
    };

    // Inicializar campos con formato Guaraníes por defecto
    let precioVentaAutoNumeric;
    if (document.getElementById('precioVenta')) {
        precioVentaAutoNumeric = new AutoNumeric('#precioVenta', guaraniesOptions);
    }

    // 3. Manejo de cambio de moneda (Ejemplo de cómo manejar diversas monedas)
    // Si tuvieras un selector de moneda, podrías hacer esto:
    /*
    $('#idMoneda').on('change', function() {
        const moneda = $(this).val(); // 'USD', 'GS', etc.
        if (moneda === 'USD') {
            precioVentaAutoNumeric.update(dolaresOptions);
        } else {
            precioVentaAutoNumeric.update(guaraniesOptions);
        }
    });
    */

    // 4. Asegurar que los datos se envíen "limpios" al servidor
    // A veces 'unformatOnSubmit' no es suficiente con Spring Boot
    $('form').on('submit', function() {
        // Recorremos todos los elementos de AutoNumeric y forzamos el "unformat"
        AutoNumeric.all().forEach(el => {
            // Esto asegura que el valor en el input sea el número puro antes del POST
            const cleanValue = el.getNumericString();
            el.node().value = cleanValue;
        });
    });

    console.log("Select2 y AutoNumeric (Multi-moneda) inicializados");
});
