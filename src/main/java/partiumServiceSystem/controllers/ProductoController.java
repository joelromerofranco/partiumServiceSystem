package partiumServiceSystem.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import partiumServiceSystem.dao.ProductoDao;
import partiumServiceSystem.dao.CategoriaDao;
import partiumServiceSystem.dao.MarcaDao;
import partiumServiceSystem.entidades.Producto;
import partiumServiceSystem.entidades.Categoria;
import partiumServiceSystem.entidades.Marca;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoDao productoDao;
    @Autowired
    private CategoriaDao categoriaDao;
    @Autowired
    private MarcaDao marcaDao;
    // Genera un nuevo producto con instancia inicializada
    private Producto crearProductoVacio() {
        Producto p = new Producto();
        p.setMarca(new Marca());
        p.setCategoria(new Categoria());
        return p;
    }
    @GetMapping
    public String inicializar(@RequestParam(required = false) String filtro, Model model) {
        if (!model.containsAttribute("producto")) {
            model.addAttribute("producto", crearProductoVacio());
    }
        if (filtro != null && !filtro.isBlank()) {
            model.addAttribute("productos", productoDao.recuperarPorFiltro(filtro));
    } 
        else {
            model.addAttribute("productos", productoDao.recuperarTodo());
    }
        cargarListas(model);
        model.addAttribute("filtro", filtro);
        model.addAttribute("mostrarFormulario", false);
        return "productos/formulario";
    }

    private void cargarListas(Model model) {
        model.addAttribute("categorias", categoriaDao.recuperarTodo());
        model.addAttribute("marcas", marcaDao.recuperarTodo());
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        if (!model.containsAttribute("producto")) {
            model.addAttribute("producto", crearProductoVacio());
        }
        model.addAttribute("productos", productoDao.recuperarTodo());
        cargarListas(model);
        model.addAttribute("mostrarFormulario", true);
        return "productos/formulario";
    }

    @GetMapping("/editar/{id}")
    public String modificar(@PathVariable Integer id, Model model) {
        Producto producto = productoDao.recuperarPorId(id);
        if (producto == null) {
            return "redirect:/productos/nuevo";
        }
        model.addAttribute("producto", producto);
        model.addAttribute("productos", productoDao.recuperarTodo());
        cargarListas(model);
        model.addAttribute("mostrarFormulario", true);
        return "productos/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Producto producto, RedirectAttributes redirectAttributes) {
        try {
            if (producto.getSigla() == null || producto.getSigla().isBlank()) {
                redirectAttributes.addFlashAttribute("error", "El campo Sigla está vacío");
                return "redirect:/productos/nuevo";
            }
            if (producto.getMarca() == null || producto.getMarca().getIdMarca() == 0) {
                redirectAttributes.addFlashAttribute("error", "Debe seleccionar una Marca");
                return "redirect:/productos/nuevo";
            }
            if (producto.getCategoria() == null || producto.getCategoria().getIdCategoria() == 0) {
                redirectAttributes.addFlashAttribute("error", "Debe seleccionar una Categoría");
                return "redirect:/productos/nuevo";
            }
            productoDao.guardar(producto);
            redirectAttributes.addFlashAttribute("mensaje", "Producto guardado correctamente");
            return "redirect:/productos";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error al guardar: " + e.getMessage());
            return "redirect:/productos/nuevo";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            Producto producto = productoDao.recuperarPorId(id);
            if (producto != null) {
                productoDao.eliminar(producto);
                redirectAttributes.addFlashAttribute("mensaje", "Producto eliminado correctamente");
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error al eliminar: " + e.getMessage());
        }
        return "redirect:/productos";
    }

    @GetMapping("/salir")
    public String salir() {
        return "redirect:/";
    }
}
