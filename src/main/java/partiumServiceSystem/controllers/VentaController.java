package partiumServiceSystem.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import partiumServiceSystem.dao.ClienteDao;
import partiumServiceSystem.dao.FuncionarioDao;
import partiumServiceSystem.dao.ProductoDao;
import partiumServiceSystem.dao.VentaDao;
import partiumServiceSystem.dao.VentaDetalleDao;
import partiumServiceSystem.entidades.Cliente;
import partiumServiceSystem.entidades.Funcionario;
import partiumServiceSystem.entidades.Venta;
import partiumServiceSystem.entidades.VentaDetalle;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaDao ventaDao;
    @Autowired
    private ClienteDao clienteDao;
    @Autowired
    private ProductoDao productoDao;
    @Autowired
    private FuncionarioDao funcionarioDao;
    @Autowired
    private VentaDetalleDao ventaDetalleDao;

    private Venta crearVentaVacia() {
        Venta v = new Venta();
        v.setFecha(LocalDateTime.now());
        v.setEstado("PRESUPUESTO");
        v.setCliente(new Cliente());
        v.setFuncionario(new Funcionario());
        v.setTotal(0.0);
        return v;
    }

    @GetMapping
    public String inicializar(@RequestParam(required = false) String filtro, Model model) {
        if (filtro != null && !filtro.isBlank()) {
            model.addAttribute("ventas", ventaDao.recuperarPorFiltro(filtro));
        } else {
            model.addAttribute("ventas", ventaDao.recuperarTodo());
        }
        model.addAttribute("filtro", filtro);
        model.addAttribute("mostrarFormulario", false);
        return "ventas/formulario";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("venta", crearVentaVacia());
        model.addAttribute("detalles", new ArrayList<VentaDetalle>());
        cargarListas(model);
        model.addAttribute("mostrarFormulario", true);
        return "ventas/formulario";
    }

    private void cargarListas(Model model) {
        model.addAttribute("clientes", clienteDao.recuperarTodo());
        model.addAttribute("productos", productoDao.recuperarTodo());
        model.addAttribute("funcionarios", funcionarioDao.recuperarTodo());
    }

    /* 
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Venta venta, @RequestParam("jsonDetalle") String jsonDetalle, RedirectAttributes redirectAttributes) {
        try {
            // 1. Guardar la Cabecera de la Venta
            // ventaDao.guardar(venta);

            // 2. Procesar los detalles (JSON -> Lista de Objetos)
            // ObjectMapper mapper = new ObjectMapper();
            // List<Map<String, Object>> listaDetalles = mapper.readValue(jsonDetalle, new TypeReference<List<Map<String, Object>>>() {});

            // for (Map<String, Object> map : listaDetalles) {
            //     VentaDetalle detalle = new VentaDetalle();
            //     detalle.setVenta(venta);
            //     detalle.setCantidad(Double.valueOf(map.get("cantidad").toString()));
            //     detalle.setPrecio(Double.valueOf(map.get("precio").toString()));
                
            //     // Buscar el producto
            //     // Producto p = productoDao.recuperarPorId(Integer.valueOf(map.get("productoId").toString()));
            //     // detalle.setProducto(p);

            //     // Guardar el detalle
            //     // ventaDetalleDao.guardar(detalle);
            // }

            // redirectAttributes.addFlashAttribute("mensaje", "Venta registrada con éxito");
            return "redirect:/ventas";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error al guardar: " + e.getMessage());
            return "redirect:/ventas/nuevo";
        }
    }
    */
}
