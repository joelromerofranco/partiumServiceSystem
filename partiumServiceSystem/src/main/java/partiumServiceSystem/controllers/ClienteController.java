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

import partiumServiceSystem.dao.ClienteDao;
import partiumServiceSystem.entidades.Cliente;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteDao clienteDao;

    // Listar todos los clientes (con filtro opcional)
    @GetMapping
    public String listar(@RequestParam(required = false) String filtro, Model model) {
        List<Cliente> lista;
        if (filtro != null && !filtro.isBlank()) {
            lista = clienteDao.recuperarPorFiltro(filtro);
        } else {
            lista = clienteDao.recuperarTodo();
        }
        model.addAttribute("clientes", lista);
        model.addAttribute("filtro", filtro);
        return "clientes/lista";
    }

    // Mostrar formulario de nuevo cliente
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/formulario";
    }

    // Guardar nuevo cliente
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Cliente cliente) throws Exception {
        clienteDao.guardar(cliente);
        return "redirect:/clientes";
    }

    // Mostrar formulario de edición
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        Cliente cliente = clienteDao.recuperarPorId(id);
        if (cliente == null) {
            return "redirect:/clientes";
        }
        model.addAttribute("cliente", cliente);
        return "clientes/formulario";
    }

    // Eliminar cliente
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) throws Exception {
        Cliente cliente = clienteDao.recuperarPorId(id);
        if (cliente != null) {
            clienteDao.eliminar(cliente);
        }
        return "redirect:/clientes";
    }
}
