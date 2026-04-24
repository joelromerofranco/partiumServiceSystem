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

import partiumServiceSystem.dao.MarcaDao;
import partiumServiceSystem.entidades.Marca;

@Controller
@RequestMapping("/marcas")
public class MarcaController {

	@Autowired
	private MarcaDao marcaDao;

	// Al inicializar el controlador y cargar la tabla
	@GetMapping
	public String inicializar(@RequestParam(required = false) String filtro, Model model) {
		if (!model.containsAttribute("marca")) {
			model.addAttribute("marca", new Marca());
		}
		if (filtro != null && !filtro.isBlank()) {
			consultarMarca(filtro, model);
		} else {
			recuperarTodo(model);
		}
		model.addAttribute("filtro", filtro);
		model.addAttribute("mostrarFormulario", false);
		return "marcas/formulario";
	}

	// Helper para recuperar todos los registros
	private void recuperarTodo(Model model) {
		List<Marca> lista = marcaDao.recuperarTodo();
		model.addAttribute("marcas", lista);
	}

	// Helper para consultar por filtro
	private void consultarMarca(String filtro, Model model) {
		List<Marca> lista = marcaDao.recuperarPorFiltro(filtro);
		model.addAttribute("marcas", lista);
	}

	// Mostrar formulario para nuevo
	@GetMapping("/nuevo")
	public String nuevo(Model model) {
		if (!model.containsAttribute("marca")) {
			model.addAttribute("marca", new Marca());
		}
		recuperarTodo(model);
		model.addAttribute("mostrarFormulario", true);
		return "marcas/formulario";
	}

	// Mostrar formulario para editar
	@GetMapping("/editar/{id}")
	public String modificar(@PathVariable Integer id, Model model) {
		Marca marca = marcaDao.recuperarPorId(id);
		if (marca == null) {
			return "redirect:/marcas/nuevo";
		}
		model.addAttribute("marca", marca);
		recuperarTodo(model);
		model.addAttribute("mostrarFormulario", true);
		return "marcas/formulario";
	}

	// Guardar o actualizar
	@PostMapping("/guardar")
	public String guardar(@ModelAttribute Marca marca, RedirectAttributes redirectAttributes) {
		try {
			if (!validarCampos(marca, redirectAttributes)) {
				// En Spring MVC, si falla la validación, solemos redirigir con errores
				// o volver a mostrar la vista. Aquí redirigimos para simplificar el flujo ABM.
				return "redirect:/marcas/nuevo";
			}
			marcaDao.guardar(marca);
			redirectAttributes.addFlashAttribute("mensaje", "Registro guardado correctamente");
			return "redirect:/marcas";
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", "Error al guardar: " + e.getMessage());
			return "redirect:/marcas/nuevo";
		}
	}

	// Eliminar
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		try {
			Marca marca = marcaDao.recuperarPorId(id);
			if (marca != null) {
				marcaDao.eliminar(marca);
				redirectAttributes.addFlashAttribute("mensaje", "Registro eliminado correctamente");
			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", "Error al eliminar: " + e.getMessage());
		}
		return "redirect:/marcas";
	}

	// Validación lógica
	private boolean validarCampos(Marca m, RedirectAttributes redirectAttributes) {
		if (m.getAbreviatura() == null || m.getAbreviatura().isBlank()) {
			redirectAttributes.addFlashAttribute("error", "El campo está vacío");
			return false;
		}
		if (m.getDescripcion() == null || m.getDescripcion().isBlank()) {
			redirectAttributes.addFlashAttribute("error", "El campo está vacío");
			return false;
		}
		if (m.getEstado() == null) {
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
