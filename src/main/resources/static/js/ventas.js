/**
 * Lógica visual para el formulario de Ventas
 */
$(document).ready(function() {
    
    // 1. Inicializar Select2
    $('.select2').select2({
        width: '100%'
    });

    // 2. Inicializar AutoNumeric para el total y el precio de item
    const currencyOptions = {
        digitGroupSeparator        : '.',
        decimalCharacter           : ',',
        decimalPlaces              : 0,
        currencySymbol             : 'Gs. ',
        currencySymbolPlacement    : 'p',
        unformatOnSubmit           : true
    };

    const itemPrecioAN = new AutoNumeric('#item-precio', currencyOptions);
    const totalVentaAN = new AutoNumeric('#total-venta-display', currencyOptions);

    // 3. Al seleccionar un producto, cargar su precio
    $('#item-producto').on('change', function() {
        const option = $(this).find(':selected');
        const precio = option.data('precio');
        if (precio) {
            itemPrecioAN.set(precio);
        } else {
            itemPrecioAN.set(0);
        }
    });

    // 4. Lógica para agregar items a la tabla (Visual)
    let totalVenta = 0;
    let listaDetalles = []; // Array para guardar los datos y enviarlos

    $('#btn-agregar-item').on('click', function() {
        const productoId = $('#item-producto').val();
        const productoText = $('#item-producto').find(':selected').text().split(' (')[0];
        const cantidad = parseFloat($('#item-cantidad').val());
        const precio = itemPrecioAN.getNumericString();
        
        if (!productoId || isNaN(cantidad) || cantidad <= 0) {
            alert('Por favor seleccione un producto y cantidad válida');
            return;
        }

        const subtotal = cantidad * parseFloat(precio);
        
        // Guardar en nuestra lista de objetos
        const item = {
            productoId: productoId,
            cantidad: cantidad,
            precio: precio
        };
        listaDetalles.push(item);

        // Remover fila de "sin datos"
        $('.sin-datos-item').hide();

        // Agregar fila a la tabla
        const fila = `
            <tr>
                <td>${productoText}</td>
                <td>${cantidad}</td>
                <td>${formatCurrency(precio)}</td>
                <td>${formatCurrency(subtotal)}</td>
                <td>
                    <button type="button" class="btn-remove" onclick="removerFila(this, ${subtotal})">❌</button>
                </td>
            </tr>
        `;
        
        $('#detalle-tabla').append(fila);

        // Actualizar total
        totalVenta += subtotal;
        actualizarTotal();
        
        // Limpiar campos de item
        $('#item-producto').val('').trigger('change');
        $('#item-cantidad').val(1);
    });

    function actualizarTotal() {
        totalVentaAN.set(totalVenta);
        $('#total-venta-input').val(totalVenta);
    }

    // Función auxiliar para formatear moneda en texto
    function formatCurrency(valor) {
        return 'Gs. ' + new Intl.NumberFormat('de-DE').format(valor);
    }

    // Global para el botón de remover
    window.removerFila = function(boton, subtotal) {
        $(boton).closest('tr').remove();
        totalVenta -= subtotal;
        actualizarTotal();
        
        if ($('#detalle-tabla tr:visible').length === 0) {
            $('.sin-datos-item').show();
        }
    };

    // 4. Asegurar que los datos se envíen "limpios" al servidor
    $('#ventaForm').on('submit', function(e) {
        // e.preventDefault(); // Descomentar para probar sin enviar realmente

        // Convertir nuestra lista de items a JSON para enviarla al controlador
        $('#jsonDetalle').val(JSON.stringify(listaDetalles));

        // Limpiar formatos de moneda para los campos de la cabecera (Total)
        const totalLimpio = totalVentaAN.getNumericString();
        $('#total-venta-input').val(totalLimpio);

        console.log("Datos a enviar:", {
            total: totalLimpio,
            detalles: listaDetalles
        });
    });

    console.log("Ventas UI inicializada");
});
