package partiumServiceSystem.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import partiumServiceSystem.dao.CompraDao;
import partiumServiceSystem.dao.ProductoDao;
import partiumServiceSystem.dao.ProveedorDao;
import partiumServiceSystem.entidades.Compra;
import partiumServiceSystem.entidades.CompraDetalle;
import partiumServiceSystem.entidades.Producto;
import partiumServiceSystem.entidades.Proveedor;

@Controller
@RequestMapping("/compras")
public class CompraController {

    @Autowired
    private CompraDao compraDao;

    @Autowired
    private ProveedorDao proveedorDao;

    @Autowired
    private ProductoDao productoDao;

    @GetMapping
    public String inicializar(@RequestParam(required = false) String filtro, Model model) {
        model.addAttribute("compras", compraDao.recuperarTodo());
        model.addAttribute("mostrarFormulario", false);
        return "compras/formulario";   // nombre de la vista principal
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("proveedores", proveedorDao.recuperarTodo());
        model.addAttribute("productos", productoDao.recuperarTodo());
        model.addAttribute("mostrarFormulario", true);
        return "compras/formulario";   // misma vista que tiene lista + formulario
    }

    @PostMapping("/guardar")
    public String guardar(
            @RequestParam Integer proveedorId,
            @RequestParam List<Integer> productoIds,
            @RequestParam List<Double> cantidades,
            @RequestParam List<Double> preciosUnitarios,
            RedirectAttributes redirectAttributes) {

        try {
            if (proveedorId == null) {
                redirectAttributes.addFlashAttribute("error", "Debe seleccionar un proveedor");
                return "redirect:/compras/nuevo";
            }

            Proveedor proveedor = proveedorDao.recuperarPorId(proveedorId);
            if (proveedor == null) {
                redirectAttributes.addFlashAttribute("error", "Proveedor no encontrado");
                return "redirect:/compras/nuevo";
            }

            Compra compra = new Compra();
            compra.setProveedor(proveedor);
            compra.setFechaCompra(LocalDate.now());
            compra.setEstado("COMPLETADA");
            compra.setTotalCompra(0.0);

            List<CompraDetalle> detalles = new ArrayList<>();
            double total = 0.0;

            for (int i = 0; i < productoIds.size(); i++) {
                Double cant = cantidades.get(i);
                if (cant == null || cant <= 0) continue;

                Producto producto = productoDao.recuperarPorId(productoIds.get(i));
                if (producto == null) continue;

                CompraDetalle detalle = new CompraDetalle();
                detalle.setProducto(producto);
                detalle.setCantidad(cant);
                detalle.setPrecioUnitario(preciosUnitarios.get(i));
                detalle.setCompra(compra);

                //compra.agregarDetalle(detalle);   // ← importante (usa el método que agregamos antes)

                // Actualizar stock
                producto.setCantidad(producto.getCantidad() + cant.intValue());

                total += cant * preciosUnitarios.get(i);
            }

            compra.setTotalCompra(total);

            compraDao.guardar(compra);

            redirectAttributes.addFlashAttribute("mensaje", "¡Compra registrada exitosamente! ID: " + compra.getIdCompras());

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error al registrar la compra: " + e.getMessage());
        }

        return "redirect:/compras/nuevo";
    }
}