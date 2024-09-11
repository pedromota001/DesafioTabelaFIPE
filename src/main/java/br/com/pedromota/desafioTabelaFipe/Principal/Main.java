package br.com.pedromota.desafioTabelaFipe.Principal;

import br.com.pedromota.desafioTabelaFipe.Modelos.Dados;
import br.com.pedromota.desafioTabelaFipe.Modelos.Modelos;
import br.com.pedromota.desafioTabelaFipe.services.ConsomeDados;
import br.com.pedromota.desafioTabelaFipe.services.ConverteDados;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    private final String ENDERECO = "https://parallelum.com.br/fipe/api/v1/";

    private ConsomeDados consomeDados = new ConsomeDados();
    private ConverteDados converteDados = new ConverteDados();
    private Scanner scanner = new Scanner(System.in);
    public void exibeMain() {
        String resp;
        do {
            System.out.println("""
                    MENU:
                    Opção 1: Carros
                    Opção 2: Caminhões
                    Opção 3: Motos
                    Opção 0: Sair
                    """);
            System.out.println("Digite a opção que você deseja: ");
            resp = scanner.nextLine();
            String endereco;
            if(resp.equalsIgnoreCase("sair")){
                System.out.println("Finalizando programa... ");
                break;
            }
            if(resp.toLowerCase().contains("carr")){
                endereco = ENDERECO + "carros/marcas";
            } else if (resp.toLowerCase().contains("mot")) {
                endereco = ENDERECO + "motos/marcas";
            } else {
                endereco = ENDERECO + "caminhoes/marcas";
            }
            var json = consomeDados.obterDados(endereco);
            var marcas = converteDados.obterLista(json, Dados.class);
            marcas.stream()
                    .sorted(Comparator.comparing(Dados::codigo))
                    .forEach(System.out::println);


            System.out.println("Agora escolha o codigo de uma marca para exibirmos todos os modelos: ");
            var codigoMarca = scanner.nextLine();
            endereco = endereco + "/" + codigoMarca + "/modelos";
            var jsonModelos = consomeDados.obterDados(endereco);
            var modelosCarro = converteDados.converteDados(jsonModelos, Modelos.class);
            System.out.println("\nModelos da marca: ");
            modelosCarro.modelos().stream()
                    .sorted(Comparator.comparing(Dados::codigo))
                    .forEach(System.out::println);
        } while (!resp.toLowerCase().equalsIgnoreCase("sair"));
    }

}
