package br.com.pedromota.desafioTabelaFipe.Principal;

import br.com.pedromota.desafioTabelaFipe.Modelos.Dados;
import br.com.pedromota.desafioTabelaFipe.Modelos.Modelos;
import br.com.pedromota.desafioTabelaFipe.Modelos.Veiculo;
import br.com.pedromota.desafioTabelaFipe.services.ConsomeDados;
import br.com.pedromota.desafioTabelaFipe.services.ConverteDados;

import java.util.ArrayList;
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
            System.out.println("Digite o nome da opcao que você deseja: ");
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
            System.out.println("Digite o trecho de um modelo: ");
            String trechoCarro = scanner.nextLine();
            modelosCarro.modelos().stream()
                    .filter(d -> d.nome().toLowerCase().contains(trechoCarro.toLowerCase()))
                    .forEach(System.out::println);

            System.out.println("Digite o codigo do modelo do veiculo que voce deseja ver: ");
            var codigoModelo = scanner.nextLine();
            endereco = endereco + "/" + codigoModelo + "/anos";
            var jsonAnosVeiculo = consomeDados.obterDados(endereco);
            var anos = converteDados.obterLista(jsonAnosVeiculo, Dados.class);

            List<Veiculo> listaVeiculos = new ArrayList<>();
            for(int i = 0; i < anos.size(); i++){
                var enderecoVeiculoAnos = endereco + "/" + anos.get(i).codigo();
                json = consomeDados.obterDados(enderecoVeiculoAnos);
                var veiculo = converteDados.converteDados(json, Veiculo.class);
                listaVeiculos.add(veiculo);
            }

            System.out.println("Lista de veiculos filtrados avaliados por ano: ");
            listaVeiculos.stream()
                    .sorted(Comparator.comparing(Veiculo::anoModelo))
                    .forEach(System.out::println);

        } while (!resp.toLowerCase().equalsIgnoreCase("sair"));
    }

}
