package models;

import java.util.*;

public class Maquina {

    private String nombre;
    private String ip;
    private List<Integer> codigos;

    public Maquina(String nombre, String ip, List<Integer> codigos) {
        this.nombre = nombre;
        this.ip = ip;
        this.codigos = codigos;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIp() {
        return ip;
    }

    public List<Integer> getCodigos() {
        return codigos;
    }

    public int getSubred() {
        String[] partes = ip.split("\\.");
        return Integer.parseInt(partes[3]); // cuarto octeto
    }

    public int getRiesgo() {
        // 1. Sumar códigos divisibles por 3
        int sumaDiv3 = 0;
        for (int c : codigos) {
            if (c % 3 == 0) sumaDiv3 += c;
        }

        // 2. Contar caracteres únicos del nombre (sin espacios)
        String nombreSinEspacios = nombre.replace(" ", "");
        Set<Character> caracteresUnicos = new HashSet<>();
        for (char ch : nombreSinEspacios.toCharArray()) {
            caracteresUnicos.add(ch);
        }

        return sumaDiv3 * caracteresUnicos.size();
    }

    @Override
    public String toString() {
        return "Maquina[nombre=" + nombre + ", ip=" + ip + ", codigos=" + codigos + 
               ", subred=" + getSubred() + ", riesgo=" + getRiesgo() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Maquina)) return false;
        Maquina other = (Maquina) o;
        return this.getNombre().equals(other.getNombre()) && this.getSubred() == other.getSubred();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNombre(), getSubred());
    }

}