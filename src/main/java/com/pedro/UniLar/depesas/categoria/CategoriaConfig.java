package com.pedro.UniLar.depesas.categoria;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class CategoriaConfig {

    private final CategoriaRepository categoriaRepository;

    private static final Map<String, String> BASE = new LinkedHashMap<>() {
        {
            put("Salário dos Funcionários",
                    "Pagamento de salários, subsídios e encargos dos colaboradores do condomínio.");
            put("Energia e Luz", "Custos de fornecimento de energia elétrica das áreas comuns.");
            put("Água", "Despesas com abastecimento de água nas áreas comuns.");
            put("Saneamento básico", "Taxas e serviços de saneamento e recolha de resíduos.");
            put("Internet e TV", "Contratos de internet e TV utilizados nas áreas comuns/gestão.");
            put("Reformas Estruturais", "Obras de manutenção e melhorias estruturais do condomínio.");
        }
    };

    @PostConstruct
    public void seedCategoriasGlobais() {
        BASE.forEach((nome, descricao) -> {
            var opt = categoriaRepository.findFirstByNomeIgnoreCaseAndCondominioIsNull(nome);
            if (opt.isPresent()) {
                Categoria c = opt.get();
                // Atualiza descrição para a versão padronizada
                c.setDescricao(descricao);
                categoriaRepository.save(c);
            } else {
                Categoria c = Categoria.builder()
                        .nome(nome)
                        .descricao(descricao)
                        .condominio(null)
                        .build();
                categoriaRepository.save(c);
            }
        });
    }
}
