package com.leonardo;

import java.util.Scanner;

public class Main {

    public static String ANSI_YELLOW = "\u001B[33m";
    public static String ANSI_RESET = "\u001B[0m";
    public static String ANSI_WHITE = "\u001B[37m";
    public static String ANSI_RED = "\u001B[31m";
    public static String ANSI_BLACK = "\u001B[30m";
    public static String[][] tabuleiro = new String[8][8];

    public static void carregarDamasVermelhasIniciais() {
        for (int linha = 0; linha < 3; linha++) {
            boolean white = linha % 2 == 0;
            for (int coluna = 0; coluna < 8; coluna++) {
                if (white) tabuleiro[linha][coluna] = "V";
                white = !white;
            }
        }
    }

    public static void carregarDamasPretasIniciais() {
        for (int linha = 5; linha < 8; linha++) {
            boolean white = linha % 2 == 0;
            for (int coluna = 0; coluna < 8; coluna++) {
                if (white) tabuleiro[linha][coluna] = "P";
                white = !white;
            }
        }
    }

    // representação gráfica:
    // cada setor é composto por 3 █ de altura e 6 █ de largura
    // o tabuleiro é 8x8 (8 setores de altura e 8 setores de largura)
    public static void imprimirNovoTabuleiro() {
        System.out.println("\n            1     2     3     4     5     6     7    8");
        for (int linha = 0; linha < 8; linha++) {
            System.out.print("         ");
            boolean white = linha % 2 == 0;
            // este for trabalha a parte de cima do setor
            for (int coluna = 0; coluna < 8; coluna++) {
                String backgroundColor = (white ? ANSI_WHITE : ANSI_YELLOW) + "██████" + ANSI_RESET;
                System.out.print(backgroundColor);
                white = !white;
            }
            System.out.println();
            System.out.print("       " + (linha + 1) + " ");
            white = linha % 2 == 0;
            // este for trabalha a parte do meio do setor, onde fica a dama
            for (int coluna = 0; coluna < 8; coluna++) {
                String backgroundColor = (white ? ANSI_WHITE : ANSI_YELLOW);
                try {
                    if (tabuleiro[linha][coluna].equals("P")) {
                        System.out.print(backgroundColor
                                         + "██" + (white ? ANSI_BLACK : "") + "██" + backgroundColor + "██"
                                         + ANSI_RESET);
                    } else if (tabuleiro[linha][coluna].equals("V")) {
                        System.out.print(backgroundColor
                                         + "██" + (white ? ANSI_RED : "") + "██" + backgroundColor + "██"
                                         + ANSI_RESET);
                    }
                } catch (Exception e) {
                    backgroundColor = (white ? ANSI_WHITE : ANSI_YELLOW) + "██████" + ANSI_RESET;
                    System.out.print(backgroundColor);
                }
                white = !white;
            }
            System.out.println();
            System.out.print("         ");
            white = linha % 2 == 0;
            // este for trabalha a parte de baixo do setor
            for (int coluna = 0; coluna < 8; coluna++) {
                String backgroundColor = (white ? ANSI_WHITE : ANSI_YELLOW) + "██████" + ANSI_RESET;
                System.out.print(backgroundColor);
                white = !white;
            }
            System.out.println();
        }
        System.out.println("\n            1     2     3     4     5     6     7    8");
    }

    public static int[][] pegarPosicoesPossiveis(int linha_dama, int coluna_dama) {
        // pode avançar no máximo duas diagonais, direita e esquerda.
        int[][] posicoes = new int[2][2];
        // orientação (cima|baixo)
        int linha_final;
        // saber o tipo da dama para descobrir a orientação possível de avanço
        String tipo_dama = tabuleiro[linha_dama][coluna_dama];
        if (tipo_dama.equals("P")) linha_final = linha_dama - 1;
        else linha_final = linha_dama + 1;

        if (coluna_dama + 1 <= 7) {
            posicoes[0][0] = linha_final;
            posicoes[0][1] = coluna_dama + 1;
        }
        if (coluna_dama - 1 >= 0) {
            posicoes[1][0] = linha_final;
            posicoes[1][1] = coluna_dama - 1;
        }
        return posicoes;
    }

    public static void movimentarDama(int linha_inicial, int coluna_inicial, int linha_destino, int coluna_destino) {
        String tipo_dama = tabuleiro[linha_inicial][coluna_inicial];
        tabuleiro[linha_inicial][coluna_inicial] = null;
        tabuleiro[linha_destino][coluna_destino] = tipo_dama;
    }

    public static void main(String[] args) {
        int linha_inicial, coluna_inicial, linha_final, coluna_final;
        Scanner sc = new Scanner(System.in);
        carregarDamasVermelhasIniciais();
        carregarDamasPretasIniciais();
        imprimirNovoTabuleiro();
        boolean nao_continua;
        do {
            System.out.println("");
            System.out.println("------ Análise de movimento ------");
            System.out.println("");
            System.out.print("Insira a linha da peça para seleção: ");
            linha_inicial = sc.nextInt() - 1;
            System.out.print("Insira a coluna da peça para seleção: ");
            coluna_inicial = sc.nextInt() - 1;

            try {
                nao_continua = tabuleiro[linha_inicial][coluna_inicial] == null;
                if (nao_continua) {
                    System.out.println(
                            ANSI_RED + "=======================================================================" +
                            ANSI_RESET);
                    System.out.println(
                            ANSI_RED + "Não foi encontrada nenhuma dama na posição indicada, tente novamente..." +
                            ANSI_RESET);
                    System.out.println(
                            ANSI_RED + "=======================================================================" +
                            ANSI_RESET);
                }
            } catch (Exception e) {
                nao_continua = true;
                System.out.println(
                        ANSI_RED + "=======================================================================" +
                        ANSI_RESET);
                System.out.println(
                        ANSI_RED + "Não foi encontrada nenhuma dama na posição indicada, tente novamente..." +
                        ANSI_RESET);
                System.out.println(
                        ANSI_RED + "=======================================================================" +
                        ANSI_RESET);
            }
        } while (nao_continua);
        int[][] posicoesPossiveis = pegarPosicoesPossiveis(linha_inicial, coluna_inicial);
        do {
            System.out.println("");
            System.out.print("Insira a linha da posição final: ");
            linha_final = sc.nextInt() - 1;
            System.out.print("Insira a coluna da posição final: ");
            coluna_final = sc.nextInt() - 1;
            try {
                nao_continua = (posicoesPossiveis[0][0] != linha_final || posicoesPossiveis[0][1] != coluna_final) &&
                               (posicoesPossiveis[1][0] != linha_final || posicoesPossiveis[1][1] != coluna_final);
                if (nao_continua) {
                    System.out.println(
                            ANSI_RED + "=======================================================================" +
                            ANSI_RESET);
                    System.out.println(
                            ANSI_RED + "A dama só pode se movimentar para suas diagonais." +
                            ANSI_RESET);
                    System.out.println(
                            ANSI_RED + "=======================================================================" +
                            ANSI_RESET);
                }

            } catch (Exception e) {
                nao_continua = true;
                System.out.println(
                        ANSI_RED + "=======================================================================" +
                        ANSI_RESET);
                System.out.println(
                        ANSI_RED + "A dama só pode se movimentar para suas diagonais." +
                        ANSI_RESET);
                System.out.println(
                        ANSI_RED + "=======================================================================" +
                        ANSI_RESET);
            }
        } while (nao_continua);
        System.out.println("");
        movimentarDama(linha_inicial, coluna_inicial, linha_final, coluna_final);
        imprimirNovoTabuleiro();
        sc.close();

    }

    // by Leonardo da Silva
    // Java 11
}
