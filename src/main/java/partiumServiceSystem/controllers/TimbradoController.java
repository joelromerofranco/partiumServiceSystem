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

import partiumServiceSystem.dao.TimbradoDao;
import partiumServiceSystem.entidades.Timbrado;

@Controller
@RequestMapping("/timbrados")
public class TimbradoController {

    @Autowired
    private TimbradoDao timbradoDao;
    // Al inicializar el controlador y cargar la tabla
    @GetMapping
    public String inicializar(@RequestParam(required = false) String filtro, Model model) {
        if (!model.containsAttribute("timbrado")) {
            model.addAttribute("timbrado", new Timbrado());
        }
        if (filtro != null && !filtro.isBlank()) {
            consultarCategoria(filtro, model);
        } else {
            recuperarTodo(model);
        }
        model.addAttribute("filtro", filtro);
        model.addAttribute("mostrarFormulario", false);
        return "timbrados/formulario";
    }

    // para recuperar todos los registros 
    private void recuperarTodo(Model model) {
        List<Timbrado> lista = timbradoDao.recuperarTodo();
        model.addAttribute("timbrados", lista);
    }

    // para consultar por filtro 
    private void consultarCategoria(String filtro, Model model) {
        List<Timbrado> lista = timbradoDao.recuperarPorFiltro(filtro);
        model.addAttribute("timbrados", lista);
    }

    // Mostrar formulario para nuevo 
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        if (!model.containsAttribute("timbrado")) {
            model.addAttribute("timbrado", new Timbrado());
        }
        recuperarTodo(model);
        model.addAttribute("mostrarFormulario", true);
        return "timbrados/formulario";
    }

    // Mostrar formulario para editar 
    @GetMapping("/editar/{id}")
    public String modificar(@PathVariable Integer id, Model model) {
        Timbrado timbrado = timbradoDao.recuperarPorId(id);
        if (timbrado == null) {
            return "redirect:/timbrados/nuevo";
        }
        model.addAttribute("timbrado", timbrado);
        recuperarTodo(model);
        model.addAttribute("mostrarFormulario", true);
        return "timbrados/formulario";
    }

    // Guardar o actualizar 
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Timbrado timbrado, RedirectAttributes redirectAttributes) {
        try {
            if (!validarCampos(timbrado, redirectAttributes)) {
                return "redirect:/timbrados/nuevo";
            }
            timbradoDao.guardar(timbrado);
			redirectAttributes.addFlashAttribute("mensaje", "Registro guardado correctamente");
            return "redirect:/timbrados";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error al guardar: " + e.getMessage());
            return "redirect:/timbrados/nuevo";
        }
    }

    // Eliminar 
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            Timbrado timbrado = timbradoDao.recuperarPorId(id);
            if (timbrado != null) {
                timbradoDao.eliminar(timbrado);
                redirectAttributes.addFlashAttribute("mensaje", "Registro eliminado correctamente");
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error al eliminar: " + e.getMessage());
        }
        return "redirect:/timbrados";
    }

    // Validación lógica 
    private boolean validarCampos(Timbrado t, RedirectAttributes redirectAttributes) {
        
        if (t.getEstado() == null ) {
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
