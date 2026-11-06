package com.migrador.controller;

import com.migrador.model.MigracaoForm;
import com.migrador.model.ResultadoMigracao;
import com.migrador.service.MigracaoStrategyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MigradorController {

    private final MigracaoStrategyService migracaoStrategyService;

    public MigradorController(MigracaoStrategyService migracaoStrategyService) {
        this.migracaoStrategyService = migracaoStrategyService;
    }

    @GetMapping({"/", "/migrador"})
    public String exibirPaginaMigrador(Model model) {
        model.addAttribute("listaTabelas", migracaoStrategyService.listarTabelasEmMemoria());
        model.addAttribute("migracaoForm", new MigracaoForm());
        return "migrador";
    }

    @PostMapping("/migrador")
    public String migrar(@ModelAttribute("migracaoForm") MigracaoForm form, Model model) {
        model.addAttribute("listaTabelas", migracaoStrategyService.listarTabelasEmMemoria());

        try {
            ResultadoMigracao resultado = migracaoStrategyService.executarMigracao(
                    form.getTabelas(),
                    form.getLimiteRegistros()
            );
            model.addAttribute("resultado", resultado);
        } catch (IllegalArgumentException ex) {
            model.addAttribute("erro", ex.getMessage());
        }

        return "migrador";
    }
}
