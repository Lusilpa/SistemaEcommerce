import javax.swing.JOptionPane;
import java.util.ArrayList;

class SistemaEcommerce {

    static class Produto {
        String nome;
        int codigo;
        double preco;

        Produto(String nome, int codigo, double preco) {
            this.nome = nome;
            this.codigo = codigo;
            this.preco = preco;
        }

        public String toString() {
            return "Produto: " + nome + "\nCódigo: " + codigo + "\nPreço: R$" + preco + "\n";
        }
    }

    static ArrayList<Produto> catalogo = new ArrayList<>();
    static ArrayList<String> relatorioVendas = new ArrayList<>();

    public static void cadastrarProduto() {
        while (true) {
            String nome = JOptionPane.showInputDialog(null, "Digite o nome do produto:");
            int codigo = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite o código do produto:"));
            double preco = Double.parseDouble(JOptionPane.showInputDialog(null, "Digite o preço do produto:"));

            Produto p = new Produto(nome, codigo, preco);
            catalogo.add(p);

            int continuar = JOptionPane.showConfirmDialog(null, "Deseja cadastrar outro produto?", "Continuar?", JOptionPane.YES_NO_OPTION);
            if (continuar != JOptionPane.YES_OPTION) break;
        }
    }

    public static void visualizarCatalogo() {
        if (catalogo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum produto cadastrado.");
            return;
        }

        StringBuilder lista = new StringBuilder();
        for (Produto p : catalogo) {
            lista.append(p.toString()).append("\n");
        }

        JOptionPane.showMessageDialog(null, lista.toString());
    }

    public static void CaixaAberto() {
        if (catalogo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum produto cadastrado no sistema.");
            return;
        }

        double totalCompra = 0.0;
        StringBuilder resumo = new StringBuilder("Resumo da Compra:\n");

        while (true) {
            try {
                int codigo = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite o CÓDIGO do produto que deseja comprar:"));

                Produto produtoSelecionado = null;
                for (Produto p : catalogo) {
                    if (p.codigo == codigo) {
                        produtoSelecionado = p;
                        break;
                    }
                }

                if (produtoSelecionado == null) {
                    JOptionPane.showMessageDialog(null, "Produto não encontrado. Tente novamente.");
                    continue;
                }

                int quantidade = Integer.parseInt(JOptionPane.showInputDialog(null, "Quantidade do produto \"" + produtoSelecionado.nome + "\":"));
                double subtotal = quantidade * produtoSelecionado.preco;
                totalCompra += subtotal;

                resumo.append(produtoSelecionado.nome)
                        .append(" | Quantidade: ").append(quantidade)
                        .append(" | Subtotal: R$").append(String.format("%.2f", subtotal)).append("\n");

                relatorioVendas.add(produtoSelecionado.nome + " | Quantidade: " + quantidade + " | Subtotal: R$" + String.format("%.2f", subtotal));

                int continuar = JOptionPane.showConfirmDialog(null, "Deseja adicionar mais produtos?", "Continuar compra", JOptionPane.YES_NO_OPTION);
                if (continuar != JOptionPane.YES_OPTION) break;

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Entrada inválida. Tente novamente.");
            }
        }

        resumo.append("\nTotal da Compra: R$").append(String.format("%.2f", totalCompra));
        JOptionPane.showMessageDialog(null, resumo.toString(), "Resumo Final", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void RelatorioDoDia() {
        if (relatorioVendas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhuma venda realizada hoje.");
            return;
        }

        StringBuilder relatorio = new StringBuilder("Relatório de Vendas do Dia:\n\n");
        for (String venda : relatorioVendas) {
            relatorio.append(venda).append("\n");
        }

        JOptionPane.showMessageDialog(null, relatorio.toString(), "Relatório Diário", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        while (true) {
            String entrada = JOptionPane.showInputDialog(
                    null,
                    "🛒 BEM-VINDO AO BASICSTORE 🛒\n\nEscolha uma opção:\n1 - Cadastrar Produto\n2 - Visualizar Catálogo\n3 - Caixa Aberto\n4 - Relatório Diário\n0 - Encerrar",
                    "BasicStore",
                    JOptionPane.PLAIN_MESSAGE
            );

            if (entrada == null) break;

            int opcao = Integer.parseInt(entrada);

            switch (opcao) {
                case 1:
                    cadastrarProduto();
                    break;
                case 2:
                    visualizarCatalogo();
                    break;
                case 3:
                    CaixaAberto();
                    break;
                case 4:
                    RelatorioDoDia();
                    break;
                case 0:
                    JOptionPane.showMessageDialog(null, "Encerrando o sistema. Até logo!");
                    System.exit(0);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida.");
            }
        }
    }
}