package bst;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

public class BSTImpl implements BST_IF {

	private Node root; // Nó raiz da árvore binária de busca

	// Construtor padrão: a raiz começa nula (árvore vazia)
	public BSTImpl() {
		// Construtor vazio
	}

	/**
	 * Verifica se a árvore está vazia.
	 * 
	 * @return true se a raiz for nula, ou false caso contrário.
	 */
	@Override
	public boolean isEmpty() {
		return this.root == null; // Retorna true se a árvore estiver vazia
	}

	/**
	 * Retorna a altura da árvore.
	 * A altura de uma árvore vazia é -1.
	 * 
	 * @return altura da árvore.
	 */
	@Override
	public int height() {
		return height(this.root); // Chama o método auxiliar passando a raiz
	}

	// Método auxiliar recursivo para calcular a altura de um nó
	private int height(Node node) {
		if (node == null)
			return -1; // Nó nulo tem altura -1
		// Altura é 1 + maior entre a altura da subárvore esquerda e direita
		return 1 + Math.max(height(node.getLeft()), height(node.getRight()));
	}

	/**
	 * Busca um valor na árvore.
	 * value valor a ser buscado.
	 * 
	 * @return o nó que contém o valor ou null se não for encontrado.
	 */
	@Override
	public Node search(Integer value) {
		return search(this.root, value); // Chama método auxiliar com a raiz
	}

	// Método auxiliar recursivo para busca
	private Node search(Node node, Integer value) {
		// Se o nó for nulo ou o valor for igual ao valor do nó, retorna o nó
		if (node == null || node.getValue().equals(value))
			return node;
		// Se o valor for menor, busca na subárvore esquerda
		if (value < node.getValue())
			return search(node.getLeft(), value);
		else
			return search(node.getRight(), value); // Caso contrário, busca à direita
	}

	/**
	 * Insere um valor na árvore.
	 * 
	 * @param value valor a ser inserido.
	 */
	@Override
	public void insert(Integer value) {
		this.root = insert(this.root, value, null); // Chama método auxiliar e atualiza a raiz
	}

	// Método auxiliar recursivo para inserção
	private Node insert(Node node, Integer value, Node parent) {
		// Se o nó atual é nulo, cria novo nó
		if (node == null) {
			Node newNode = new Node();
			newNode.setValue(value); // Define valor do novo nó
			newNode.setParent(parent); // Define o pai do novo nó
			return newNode;
		}
		// Se o valor é menor, insere na esquerda
		if (value < node.getValue()) {
			node.setLeft(insert(node.getLeft(), value, node));
		} else if (value > node.getValue()) {
			// Se o valor é maior, insere na direita
			node.setRight(insert(node.getRight(), value, node));
		} // Se for igual, não insere (valores duplicados são ignorados)
		return node;
	}

	/**
	 * Retorna o maior valor a partir de um nó.
	 * node nó de referência.
	 * 
	 * @return o nó com maior valor.
	 */
	@Override
	public Node maximum(Node node) {
		if (node == null)
			return null; // Árvore vazia
		// Continua indo para a direita até encontrar o último nó
		while (node.getRight() != null)
			node = node.getRight();
		return node;
	}

	/**
	 * Retorna o menor valor a partir de um nó.
	 * 
	 * @param node nó de referência.
	 * @return o nó com menor valor.
	 */
	@Override
	public Node minimum(Node node) {
		if (node == null)
			return null; // Árvore vazia
		// Continua indo para a esquerda até encontrar o menor valor
		while (node.getLeft() != null)
			node = node.getLeft();
		return node;
	}

	/**
	 * Retorna o predecessor de um nó.
	 * node nó de referência.
	 * 
	 * @return nó predecessor ou null.
	 */
	@Override
	public Node predecessor(Node node) {
		// Se tem filho à esquerda, retorna o máximo da subárvore esquerda
		if (node.getLeft() != null)
			return maximum(node.getLeft());
		// Caso contrário, sobe na árvore até encontrar um ancestral à direita
		Node parent = node.getParent();
		while (parent != null && node == parent.getLeft()) {
			node = parent;
			parent = parent.getParent();
		}
		return parent;
	}

	/**
	 * Retorna o sucessor de um nó.
	 * 
	 * @param node nó de referência.
	 * @return nó sucessor ou null.
	 */
	@Override
	public Node sucessor(Node node) {
		// Se tem filho à direita, retorna o mínimo da subárvore direita
		if (node.getRight() != null)
			return minimum(node.getRight());
		// Caso contrário, sobe na árvore até encontrar um ancestral à esquerda
		Node parent = node.getParent();
		while (parent != null && node == parent.getRight()) {
			node = parent;
			parent = parent.getParent();
		}
		return parent;
	}

	/**
	 * Remove um valor da árvore.
	 * value valor a ser removido.
	 */
	@Override
	public void remove(Integer value) {
		this.root = remove(this.root, value); // Chama o método auxiliar e atualiza a raiz
	}

	// Método auxiliar recursivo para remoção
	private Node remove(Node node, Integer value) {
		if (node == null)
			return null; // Valor não encontrado
		if (value < node.getValue()) {
			// Busca na esquerda
			node.setLeft(remove(node.getLeft(), value));
		} else if (value > node.getValue()) {
			// Busca na direita
			node.setRight(remove(node.getRight(), value));
		} else {
			// Nó com valor encontrado
			if (node.getLeft() == null && node.getRight() == null) {
				return null; // Caso 1: nó folha
			} else if (node.getLeft() == null) {
				// Caso 2: só tem filho à direita
				node.getRight().setParent(node.getParent());
				return node.getRight();
			} else if (node.getRight() == null) {
				// Caso 2: só tem filho à esquerda
				node.getLeft().setParent(node.getParent());
				return node.getLeft();
			} else {
				// Caso 3: dois filhos
				Node successor = minimum(node.getRight()); // Encontra sucessor
				node.setValue(successor.getValue()); // Copia valor do sucessor
				node.setRight(remove(node.getRight(), successor.getValue())); // Remove o sucessor
			}
		}
		return node;
	}

	/**
	 * Retorna os valores da árvore em pré-ordem.
	 */
	@Override
	public Integer[] preOrder() {
		ArrayList<Integer> list = new ArrayList<>(); // Lista para armazenar resultado
		preOrder(this.root, list); // Chama método auxiliar
		return list.toArray(new Integer[0]); // Converte lista para array
	}

	// Método auxiliar para percurso em pré-ordem (raiz, esquerda, direita)
	private void preOrder(Node node, ArrayList<Integer> list) {
		if (node != null) {
			list.add(node.getValue());
			preOrder(node.getLeft(), list);
			preOrder(node.getRight(), list);
		}
	}

	/**
	 * Retorna os valores da árvore em ordem (in-order).
	 */
	@Override
	public Integer[] order() {
		ArrayList<Integer> list = new ArrayList<>();
		order(this.root, list);
		return list.toArray(new Integer[0]);
	}

	// Método auxiliar para percurso em ordem (esquerda, raiz, direita)
	private void order(Node node, ArrayList<Integer> list) {
		if (node != null) {
			order(node.getLeft(), list);
			list.add(node.getValue());
			order(node.getRight(), list);
		}
	}

	/**
	 * Retorna os valores da árvore em pós-ordem.
	 */
	@Override
	public Integer[] postOrder() {
		ArrayList<Integer> list = new ArrayList<>();
		postOrder(this.root, list);
		return list.toArray(new Integer[0]);
	}

	// Método auxiliar para percurso em pós-ordem (esquerda, direita, raiz)
	private void postOrder(Node node, ArrayList<Integer> list) {
		if (node != null) {
			postOrder(node.getLeft(), list);
			postOrder(node.getRight(), list);
			list.add(node.getValue());
		}
	}

	/**
	 * Retorna o número total de nós da árvore.
	 */
	@Override
	public int size() {
		return size(this.root); // Chama método auxiliar
	}

	// Método auxiliar recursivo para contar nós
	private int size(Node node) {
		if (node == null)
			return 0; // Nó nulo não conta
		return 1 + size(node.getLeft()) + size(node.getRight()); // Soma recursiva
	}

	/**
	 * Método de brinde! Não modificar!
	 * Este método implementa uma busca em largura usando uma fila.
	 * 
	 * @return
	 */
	public ArrayList<Integer> bfs() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		Deque<Node> queue = new LinkedList<Node>();

		if (!isEmpty()) {
			queue.addLast(this.root);
			while (!queue.isEmpty()) {
				Node current = queue.removeFirst();

				list.add(current.getValue());

				if (current.getLeft() != null)
					queue.addLast(current.getLeft());
				if (current.getRight() != null)
					queue.addLast(current.getRight());
			}
		}
		return list;
	}

}
