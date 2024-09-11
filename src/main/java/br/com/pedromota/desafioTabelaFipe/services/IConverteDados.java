package br.com.pedromota.desafioTabelaFipe.services;

public interface IConverteDados {
    <T> T converteDados(String json, Class<T> classe);
}
