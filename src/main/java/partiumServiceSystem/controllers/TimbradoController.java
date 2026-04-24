/*package partiumServiceSystem.controllers;

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

import partiumServiceSystem.dao.CategoriaDao;
import partiumServiceSystem.entidades.Categoria;

@Controller
@RequestMapping("/categorias")
public class TimbradoController {

    @Autowired
    private CategoriaDao categoriaDao;
    // Al inicializar el controlador y cargar la tabla
    @GetMapping
    public String inicializar(@RequestParam(required = false) String filtro, Model model) {
        if (!model.containsAttribute("categoria")) {
            model.addAttribute("categoria", new Categoria());
        }
        if (filtro != null && !filtro.isBlank()) {
            consultarCategoria(filtro, model);
        } else {
            recuperarTodo(model);
        }
        model.addAttribute("filtro", filtro);
        model.addAttribute("mostrarFormulario", false);
        return "categorias/formulario";
    }

    // Helper para recuperar todos los registros 
    private void recuperarTodo(Model model) {
        List<Categoria> lista = categoriaDao.recuperarTodo();
        model.addAttribute("categorias", lista);
    }

    // Helper para consultar por filtro 
    private void consultarCategoria(String filtro, Model model) {
        List<Categoria> lista = categoriaDao.recuperarPorFiltro(filtro);
        model.addAttribute("categorias", lista);
    }

    // Mostrar formulario para nuevo 
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        if (!model.containsAttribute("categoria")) {
            model.addAttribute("categoria", new Categoria());
        }
        recuperarTodo(model);
        model.addAttribute("mostrarFormulario", true);
        return "categorias/formulario";
    }

    // Mostrar formulario para editar 
    @GetMapping("/editar/{id}")
    public String modificar(@PathVariable Integer id, Model model) {
        Categoria categoria = categoriaDao.recuperarPorId(id);
        if (categoria == null) {
            return "redirect:/categorias/nuevo";
        }
        model.addAttribute("categoria", categoria);
        recuperarTodo(model);
        model.addAttribute("mostrarFormulario", true);
        return "categorias/formulario";
    }

    // Guardar o actualizar 
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Categoria categoria, RedirectAttributes redirectAttributes) {
        try {
            if (!validarCampos(categoria, redirectAttributes)) {
                // En Spring MVC, si falla la validación, solemos redirigir con errores
                // o volver a mostrar la vista. Aquí redirigimos para simplificar el flujo ABM.
                return "redirect:/categorias/nuevo";
            }
            categoriaDao.guardar(categoria);
			redirectAttributes.addFlashAttribute("mensaje", "Registro guardado correctamente");
            return "redirect:/categorias";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error al guardar: " + e.getMessage());
            return "redirect:/categorias/nuevo";
        }
    }

    // Eliminar 
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            Categoria categoria = categoriaDao.recuperarPorId(id);
            if (categoria != null) {
                categoriaDao.eliminar(categoria);
                redirectAttributes.addFlashAttribute("mensaje", "Registro eliminado correctamente");
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error al eliminar: " + e.getMessage());
        }
        return "redirect:/categorias";
    }

    // Validación lógica 
    private boolean validarCampos(Categoria c, RedirectAttributes redirectAttributes) {
        if (c.getAbreviatura() == null || c.getAbreviatura().isBlank()) {
            redirectAttributes.addFlashAttribute("error", "El campo está vacío");
            return false;
        }
        if (c.getDescripcion() == null || c.getDescripcion().isBlank()) {
            redirectAttributes.addFlashAttribute("error", "El campo está vacío");
            return false;
        }
        if (c.getEstado() == null ) {
            redirectAttributes.addFlashAttribute("error", "El campo está vacío");
            return false;
        
        }
        return true;
    }

    // Salir 
    @GetMapping("/salir")
    public String salir() {
        return "redirect:/";
    }
}
*/