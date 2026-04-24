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

import partiumServiceSystem.dao.ProveedorDao;
import partiumServiceSystem.entidades.Proveedor;

@Controller
@RequestMapping("/proveedores")
public class ProveedorController {

	@Autowired
	private ProveedorDao proveedorDao;

	// Al inicializar el controlador y cargar la tabla
	@GetMapping
	public String inicializar(@RequestParam(required = false) String filtro, Model model) {
		if (!model.containsAttribute("proveedor")) {
			model.addAttribute("proveedor", new Proveedor());
		}
		if (filtro != null && !filtro.isBlank()) {
			consultarProveedor(filtro, model);
		} else {
			recuperarTodo(model);
		}
		model.addAttribute("filtro", filtro);
		model.addAttribute("mostrarFormulario", false);
		return "proveedores/formulario";
	}

	// Helper para recuperar todos los registros
	private void recuperarTodo(Model model) {
		List<Proveedor> lista = proveedorDao.recuperarTodo();
		model.addAttribute("proveedores", lista);
	}

	// Helper para consultar por filtro
	private void consultarProveedor(String filtro, Model model) {
		List<Proveedor> lista = proveedorDao.recuperarPorFiltro(filtro);
		model.addAttribute("proveedores", lista);
	}

	// Mostrar formulario para nuevo
	@GetMapping("/nuevo")
	public String nuevo(Model model) {
		if (!model.containsAttribute("proveedor")) {
			model.addAttribute("proveedor", new Proveedor());
		}
		recuperarTodo(model);
		model.addAttribute("mostrarFormulario", true);
		return "proveedores/formulario";
	}

	// Mostrar formulario para editar
	@GetMapping("/editar/{id}")
	public String modificar(@PathVariable Integer id, Model model) {
		Proveedor proveedor = proveedorDao.recuperarPorId(id);
		if (proveedor == null) {
			return "redirect:/proveedores/nuevo";
		}
		model.addAttribute("proveedor", proveedor);
		recuperarTodo(model);
		model.addAttribute("mostrarFormulario", true);
		return "proveedores/formulario";
	}

	// Guardar o actualizar
	@PostMapping("/guardar")
	public String guardar(@ModelAttribute Proveedor proveedor, RedirectAttributes redirectAttributes) {
		try {
			if (!validarCampos(proveedor, redirectAttributes)) {
				// En Spring MVC, si falla la validación, solemos redirigir con errores
				// o volver a mostrar la vista. Aquí redirigimos para simplificar el flujo ABM.
				return "redirect:/proveedores/nuevo";
			}
			proveedorDao.guardar(proveedor);
			redirectAttributes.addFlashAttribute("mensaje", "Proveedor guardado correctamente");
			return "redirect:/proveedores";
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", "Error al guardar: " + e.getMessage());
			return "redirect:/proveedores/nuevo";
		}
	}

	// Eliminar
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		try {
			Proveedor proveedor = proveedorDao.recuperarPorId(id);
			if (proveedor != null) {
				proveedorDao.eliminar(proveedor);
				redirectAttributes.addFlashAttribute("mensaje", "Registro eliminado correctamente");
			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", "Error al eliminar: " + e.getMessage());
		}
		return "redirect:/proveedores";
	}

	// Validación lógica
	private boolean validarCampos(Proveedor pr, RedirectAttributes redirectAttributes) {
		if (pr.getRazonSocial() == null || pr.getRazonSocial().isBlank()) {
			redirectAttributes.addFlashAttribute("error", "El campo Nombre o Razon social  está vacío");
			return false;
		}
		if (pr.getDireccion() == null || pr.getDireccion().isBlank()) {
			redirectAttributes.addFlashAttribute("error", "El campo Dirección está vacío");
			return false;
		}
		if (pr.getDocumento() == null || pr.getDocumento().isBlank()) {
			redirectAttributes.addFlashAttribute("error", "El campo Documento-Ruc está vacío");
			return false;
		}
		// Verificación de duplicados
		Proveedor existente = proveedorDao.recuperarPorFiltro(pr.getDocumento()).stream()
				.filter(p -> pr.getDocumento().equals(p.getDocumento())).findFirst().orElse(null);

		if (existente != null) {
			if (pr.getIdProveedor() == null) { // Es nuevo
				redirectAttributes.addFlashAttribute("error", "Documento duplicado");
				return false;
			} else if (!pr.getIdProveedor().equals(existente.getIdProveedor())) { // Es edición y el documento es de otro
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
