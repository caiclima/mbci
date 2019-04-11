/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package extra;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * @author Claudinei
 */
public class Extra {

	SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");

	private void imp4MaisVelhos(List<Amigo> todosAmigosList) throws ParseException {

		if (todosAmigosList.size() > 4 ) {

			List<Amigo> amigosMaisVelhos = new ArrayList<Amigo>();

			for (Amigo amigoAtual : todosAmigosList) {
				if(amigoAtual.getSexo().equals('M')){
					verificaSeAmigoEntraNaListaDosMaiores(amigosMaisVelhos, amigoAtual);
				}
			}
			
			
			
			exibeAmigos(amigosMaisVelhos, "LISTA DOS 4 AMIGOS MAIS VELHOS");

		}
	}

	private List<Amigo> verificaSeAmigoEntraNaListaDosMaiores(
			List<Amigo> amigoList, Amigo amigoAtual) {

		Amigo amigoMaisNovo = null;

		if (amigoList.size() < 4) {
			amigoList.add(amigoAtual);
		} else {
			amigoMaisNovo = buscaAmigoMaisNovo(amigoList);

			if (amigoAtual.getDataNascimento().before(
					amigoMaisNovo.getDataNascimento())) {

				for (Iterator<Amigo> iterator2 = amigoList.iterator(); iterator2.hasNext();) {
					Amigo amigoASubstituir = (Amigo) iterator2.next();
					if (amigoASubstituir.equals(amigoMaisNovo)) {
						iterator2.remove();
					}

				}
				amigoList.add(amigoAtual);
			}
		}

		return amigoList;
	}

	private Amigo buscaAmigoMaisNovo(List<Amigo> amigoList) {
		Amigo amigoMaisNovo = null;
		for (Amigo amigo : amigoList) {
			if (amigoMaisNovo == null) {
				amigoMaisNovo = amigo;
			} else {
				if (amigo.getDataNascimento().before(amigo.getDataNascimento())) {
					amigoMaisNovo = amigo;
				}
			}
		}
		return amigoMaisNovo;
	}

	private void imp6MulherNomeMaior(List<Amigo> vetAmigos) {

		// ESTE METODO DEVE IMPRIMIR AS 6 MULHERES COM OS MAIORES NOMES [EM
		// NUMERO DE CARACTERES]
		List<Amigo> MulheresMaiorNomeList = new ArrayList<Amigo>();
		List<Integer> posicaoMulheresMaiorNomeList = new ArrayList<Integer>();
		for (int h = 0; h < 6; h++) {
			Integer posicaoMulherMaiorNome = null;
			
			for (int i = 0; i < vetAmigos.size(); i++) {
				
				
				Amigo amigoAtual = vetAmigos.get(i);
				if(amigoAtual.getSexo().equals('M')) {
					
					if(posicaoMulherMaiorNome == null){
						posicaoMulherMaiorNome = i;
					} else {
						if(amigoAtual.getNome().length() > vetAmigos.get(posicaoMulherMaiorNome).getNome().length()) {
							posicaoMulherMaiorNome = i;
						}
					}
					
				}
			}
			if(posicaoMulherMaiorNome != null && !posicaoMulheresMaiorNomeList.contains(posicaoMulherMaiorNome)) {
				posicaoMulheresMaiorNomeList.add(posicaoMulherMaiorNome);
				MulheresMaiorNomeList.add(vetAmigos.get(posicaoMulherMaiorNome));
				vetAmigos.remove(vetAmigos.get(posicaoMulherMaiorNome));
			}
		}
		
		exibeAmigos(MulheresMaiorNomeList, "MULHERES COM MAIOR NOME");

		/*String nome = " ";
		String aux = "";
		char sexo = 'F';
		if (vetAmigos.size() < 6) {
			for (Amigo A : vetAmigos) {
				if (A.getSexo() == sexo) {
					for (int i = 0; i < A.getNome().length(); i++) {
						if (A.getNome().length() > aux.length()) {
							aux = nome;

						}
					}
				}
			}
		}*/
	}
	
	private int buscaMulherComMaiorIdade(List<Amigo> amigoList){
		Integer posicaoMulherMaiorNome = null;
		for (int i = 0; i < amigoList.size(); i++) {
			if(posicaoMulherMaiorNome == null){
				posicaoMulherMaiorNome = i;
			} else {
				if(amigoList.get(i).getNome().length() > amigoList.get(posicaoMulherMaiorNome).getNome().length()) {
					posicaoMulherMaiorNome = i;
				}
			}
		}
		return posicaoMulherMaiorNome;
	}

	public static void main(String[] args) {
		Extra extra = new Extra();
		List<Amigo> amigoList = extra.criaAmigos();
		extra.imp6MulherNomeMaior(amigoList);

		try {
			extra.imp4MaisVelhos(amigoList);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	private List<Amigo> criaAmigos() {

		try {

			int dia = 1;
			int mes = 1;
			int ano = 2000;
			Character sexo = null;
			String sufixoNome = "";

			List<Amigo> amigoList = new ArrayList<Amigo>();
			

			for (int i = 1; i <= 20; i++) {

				sufixoNome+=i;
				
				dia += i;
				mes += i;
				ano -= i;
				sexo = 'M';
				if(i%2 == 0) {
					ano = ano - i;
					sexo = 'F';
				}
				
				Amigo amigo = new Amigo("NOME - " + sufixoNome, "EMAIL - " + i, "CPF" + i, sf.parse(dia + "/" + mes + "/" + ano), sexo);
				amigoList.add(amigo);

			}
			exibeAmigos(amigoList, "LISTA DE TODOS OS AMIGOS");
			
			

			return amigoList;

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	private void exibeAmigos(List<Amigo> amigoList, String descricao) {
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("========= " + descricao + " ========= ");
		for (Amigo amigo : amigoList) {
			System.out.println(amigo.getNome() + "   " + amigo.getEmail() + "   " + sf.format(amigo.getDataNascimento()) + "   " + amigo.getSexo());
		}
		System.out.println("========= " + descricao + " ========= ");
	}
}
