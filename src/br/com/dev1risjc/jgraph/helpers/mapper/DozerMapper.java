package br.com.dev1risjc.jgraph.helpers.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DozerMapper {
    private static final Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    public static <O, D> D parseObject(O origem, Class<D> destino) {
        return mapper.map(origem, destino);
    }

    public static <O, D> List<D> parseListObject(List<O> listaOrigem, Class<D> destino) {
        List<D> listaDestino = new ArrayList<>();
        for (O o : listaOrigem) {
            listaDestino.add(mapper.map(o, destino));
        }
        return listaDestino;
    }

    public static <O, D> HashMap<D, D> parseHashMapObject(HashMap<O, O> listaOrigem, Class<D> destino) {
        HashMap<D, D> listaDestino = new HashMap<>();
        for (O o : listaOrigem.keySet()) {
            listaDestino.put(mapper.map(o, destino), mapper.map(listaOrigem.get(o), destino));
        }
        return listaDestino;
    }
}
