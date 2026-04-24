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

import partiumServiceSystem.dao.FuncionarioDao;
import partiumServiceSystem.entidades.Funcionario;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioDao funcionarioDao;

    // Al inicializar el controlador y cargar la tabla
    @GetMapping
    public String inicializar(@RequestParam(required = false) String filtro, Model model) {
        if (!model.containsAttribute("funcionario")) {
            model.addAttribute("funcionario", new Funcionario());
        }
        if (filtro != null && !filtro.isBlank()) {
            consultarFuncionario(filtro, model);
        } else {
            recuperarTodo(model);
        }
        model.addAttribute("filtro", filtro);
        model.addAttribute("mostrarFormulario", false);
        return "funcionarios/formulario";
    }

    // Helper para recuperar todos los registros 
    private void recuperarTodo(Model model) {
        List<Funcionario> lista = funcionarioDao.recuperarTodo();
        model.addAttribute("funcionarios", lista);
    }

    // Helper para consultar por filtro 
    private void consultarFuncionario(String filtro, Model model) {
        List<Funcionario> lista = funcionarioDao.recuperarPorFiltro(filtro);
        model.addAttribute("funcionarios", lista);
    }

    // Mostrar formulario para nuevo 
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        if (!model.containsAttribute("funcionario")) {
            model.addAttribute("funcionario", new Funcionario());
        }
        recuperarTodo(model);
        model.addAttribute("mostrarFormulario", true);
        return "funcionarios/formulario";
    }

    // Mostrar formulario para editar 
    @GetMapping("/editar/{id}")
    public String modificar(@PathVariable Integer id, Model model) {
        Funcionario funcionario = funcionarioDao.recuperarPorId(id);
        if (funcionario == null) {
            return "redirect:/funcionarios/nuevo";
        }
        model.addAttribute("funcionario", funcionario);
        recuperarTodo(model);
        model.addAttribute("mostrarFormulario", true);
        return "funcionarios/formulario";
    }

    // Guardar o actualizar 
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Funcionario funcionario, RedirectAttributes redirectAttributes) {
        try {
            if (!validarCampos(funcionario, redirectAttributes)) {
                // En Spring MVC, si falla la validación, solemos redirigir con errores
                // o volver a mostrar la vista. Aquí redirigimos para simplificar el flujo ABM.
                return "redirect:/funcionarios/nuevo";
            }
            funcionarioDao.guardar(funcionario);
            redirectAttributes.addFlashAttribute("mensaje", "Funcionario guardado correctamente");
            return "redirect:/funcionarios";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error al guardar: " + e.getMessage());
            return "redirect:/funcionarios/nuevo";
        }
    }

    // Eliminar 
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            Funcionario funcionario = funcionarioDao.recuperarPorId(id);
            if (funcionario != null) {
                funcionarioDao.eliminar(funcionario);
                redirectAttributes.addFlashAttribute("mensaje", "Funcionario eliminado correctamente");
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error al eliminar: " + e.getMessage());
        }
        return "redirect:/funcionarios";
    }

    // Validación lógica 
    private boolean validarCampos(Funcionario f, RedirectAttributes redirectAttributes) {
        if (f.getNombre() == null || f.getNombre().isBlank()) {
            redirectAttributes.addFlashAttribute("error", "El campo Nombre está vacío");
            return false;
        }
        if (f.getApellido() == null || f.getApellido().isBlank()) {
            redirectAttributes.addFlashAttribute("error", "El campo Apellido está vacío");
            return false;
        }
        if (f.getDocumento() == null || f.getDocumento().isBlank()) {
            redirectAttributes.addFlashAttribute("error", "El campo Documento está vacío");
            return false;
        }

        // Verificación de duplicados 
        Funcionario existente = funcionarioDao.recuperarPorFiltro(f.getDocumento()).stream()
                .filter(p -> p.getDocumento().equals(f.getDocumento()))
                .findFirst().orElse(null);

        if (existente != null) {
            if (f.getIdPersona() == null) { // Es nuevo
                redirectAttributes.addFlashAttribute("error", "Documento duplicado");
                return false;
            } else if (!f.getIdPersona().equals(existente.getIdPersona())) { // Es edición y el documento es de otro
                redirectAttributes.addFlashAttribute("error", "Documento duplicado");
                return false;
            }
        }
        return true;
    }

    // Salir 
    @GetMapping("/salir")
    public String salir() {
        return "redirect:/";
    }
}
