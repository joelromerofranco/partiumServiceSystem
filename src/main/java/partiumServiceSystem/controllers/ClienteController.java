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

import partiumServiceSystem.dao.ClienteDao;
import partiumServiceSystem.entidades.Cliente;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteDao clienteDao;

    // Al inicializar el controlador y cargar la tabla
    @GetMapping
    public String inicializar(@RequestParam(required = false) String filtro, Model model) {
        if (!model.containsAttribute("cliente")) {
            model.addAttribute("cliente", new Cliente());
        }
        if (filtro != null && !filtro.isBlank()) {
            consultarCliente(filtro, model);
        } else {
            recuperarTodo(model);
        }
        model.addAttribute("filtro", filtro);
        model.addAttribute("mostrarFormulario", false);
        return "clientes/formulario";
    }

    // Helper para recuperar todos los registros 
    private void recuperarTodo(Model model) {
        List<Cliente> lista = clienteDao.recuperarTodo();
        model.addAttribute("clientes", lista);
    }

    // Helper para consultar por filtro 
    private void consultarCliente(String filtro, Model model) {
        List<Cliente> lista = clienteDao.recuperarPorFiltro(filtro);
        model.addAttribute("clientes", lista);
    }

    // Mostrar formulario para nuevo 
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        if (!model.containsAttribute("cliente")) {
            model.addAttribute("cliente", new Cliente());
        }
        recuperarTodo(model);
        model.addAttribute("mostrarFormulario", true);
        return "clientes/formulario";
    }

    // Mostrar formulario para editar 
    @GetMapping("/editar/{id}")
    public String modificar(@PathVariable Integer id, Model model) {
        Cliente cliente = clienteDao.recuperarPorId(id);
        if (cliente == null) {
            return "redirect:/clientes/nuevo";
        }
        model.addAttribute("cliente", cliente);
        recuperarTodo(model);
        model.addAttribute("mostrarFormulario", true);
        return "clientes/formulario";
    }

    // Guardar o actualizar 
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Cliente cliente, RedirectAttributes redirectAttributes) {
        try {
            if (!validarCampos(cliente, redirectAttributes)) {
                // En Spring MVC, si falla la validación, solemos redirigir con errores
                // o volver a mostrar la vista. Aquí redirigimos para simplificar el flujo ABM.
                return "redirect:/clientes/nuevo";
            }
            clienteDao.guardar(cliente);
			redirectAttributes.addFlashAttribute("mensaje", "Cliente guardado correctamente");
            return "redirect:/clientes";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error al guardar: " + e.getMessage());
            return "redirect:/clientes/nuevo";
        }
    }

    // Eliminar 
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            Cliente cliente = clienteDao.recuperarPorId(id);
            if (cliente != null) {
                clienteDao.eliminar(cliente);
                redirectAttributes.addFlashAttribute("mensaje", "Registro eliminado correctamente");
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error al eliminar: " + e.getMessage());
        }
        return "redirect:/clientes";
    }

    // Validación lógica 
    private boolean validarCampos(Cliente c, RedirectAttributes redirectAttributes) {
        if (c.getNombre() == null || c.getNombre().isBlank()) {
            redirectAttributes.addFlashAttribute("error", "El campo Nombre está vacío");
            return false;
        }
        if (c.getApellido() == null || c.getApellido().isBlank()) {
            redirectAttributes.addFlashAttribute("error", "El campo Apellido está vacío");
            return false;
        }
        if (c.getDocumento() == null || c.getDocumento().isBlank()) {
            redirectAttributes.addFlashAttribute("error", "El campo Documento está vacío");
            return false;
        }
        // Verificación de duplicados 
        Cliente existente = clienteDao.recuperarPorFiltro(c.getDocumento()).stream()
                .filter(p -> p.getDocumento().equals(c.getDocumento()))
                .findFirst().orElse(null);

        if (existente != null) {
            if (c.getIdPersona() == null) { // Es nuevo
                redirectAttributes.addFlashAttribute("error", "Documento duplicado");
                return false;
            } else if (!c.getIdPersona().equals(existente.getIdPersona())) { // Es edición y el documento es de otro
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
