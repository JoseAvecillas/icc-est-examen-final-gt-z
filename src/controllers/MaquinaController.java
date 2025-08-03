package controllers;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;

import models.Maquina;

public class MaquinaController {

     public Stack<Maquina> filtrarPorSubred(List<Maquina> maquinas, int umbral) {
        Stack<Maquina> resultado = new Stack<>();
        for (Maquina m : maquinas) {
            if (m.getSubred() < umbral) {
                resultado.add(m); 
            }
        }
        return resultado;

    }

    public Set<Maquina> ordenarPorSubred(List<Maquina> maquinas) {
        Comparator<Maquina> comparador = Comparator.comparingInt(Maquina::getSubred).thenComparing(Maquina::getNombre);

        TreeSet<Maquina> resultado = new TreeSet<>(comparador);

        for (Maquina m : maquinas) {
            resultado.add(m);  // TreeSet automáticamente evita duplicados
        }

        return resultado;
    }

    public TreeMap<Integer, Queue<Maquina>> agruparPorRiesgo(List<Maquina> maquinas) {
        TreeMap<Integer, Queue<Maquina>> mapa = new TreeMap<>();

        for (Maquina m : maquinas) {
            int riesgo = m.getRiesgo();

            // Si ya existe el riesgo, añadimos a la cola
            if (mapa.containsKey(riesgo)) {
                mapa.get(riesgo).add(m);
            } else {
                // Si no existe, creamos nueva cola y la insertamos
                Queue<Maquina> nuevaCola = new LinkedList<>();
                nuevaCola.add(m);
                mapa.put(riesgo, nuevaCola);
            }
        }

        return mapa;
    }

    public Stack<Maquina> explotarGrupo(Map<Integer, Queue<Maquina>> mapa) {
        int maxCantidad = -1;
        int riesgoSeleccionado = -1;
        Queue<Maquina> grupoSeleccionado = null;

        for (Map.Entry<Integer, Queue<Maquina>> entry : mapa.entrySet()) {
            int riesgo = entry.getKey();
            Queue<Maquina> grupo = entry.getValue();
            int cantidad = grupo.size();

            // Elegir si:
            // 1. Tiene más elementos
            // 2. O está empatado en cantidad pero tiene mayor riesgo
            if (cantidad > maxCantidad || (cantidad == maxCantidad && riesgo > riesgoSeleccionado)) {
                maxCantidad = cantidad;
                riesgoSeleccionado = riesgo;
                grupoSeleccionado = grupo;
            }
        }

        // Convertimos la cola seleccionada a Stack (LIFO)
        Stack<Maquina> resultado = new Stack<>();
        if (grupoSeleccionado != null) {
            for (Maquina m : grupoSeleccionado) {
                resultado.push(m);
            }
        }

        return resultado;
    }
}
