package br.com.pedromota.desafioTabelaFipe.Principal;

import br.com.pedromota.desafioTabelaFipe.services.ConsomeDados;
import br.com.pedromota.desafioTabelaFipe.services.ConverteDados;

import java.util.Scanner;

public class Main {
    private final String ENDERECO = "https://parallelum.com.br/fipe/api/v1/";
    private ConsomeDados consomeDados = new ConsomeDados();
    private ConverteDados converteDados = new ConverteDados();
    private Scanner scanner = new Scanner(System.in);
    public void exibeMain() {
        int resp = 1;
        do {
            System.out.println("""
                    MENU:
                    Opção 1: Carros
                    Opção 2: Caminhões
                    Opção 3: Motos
                    Opção 0: Sair
                    """);
            System.out.println("Digite a opção que você deseja: ");
            resp = scanner.nextInt();
            scanner.nextLine();
            switch (resp){
                case 1:
                    consomeDados.obterDados(ENDERECO + "carros/marcas");
                case 2:
                    consomeDados.obterDados(ENDERECO + "caminhoes/marcas");
                case 3:
                    consomeDados.obterDados(ENDERECO + "motos/marcas");
                case 0:
                    System.out.println("Finalizando sistema...");
                    break;
            }
        } while (resp != 0);
    }

}
