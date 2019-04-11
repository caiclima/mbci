package br.com.mbci12.bb;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class GenericBB {
	

	private String pathProject = "/mbci12-web/";
	
	

	public final String getPathProject() {
		return pathProject;
	}

	public final void setPathProject(String pathProject) {
		this.pathProject = pathProject;
	}
	
	public void redirecionaPaginaInicial() {
		redireciona(pathProject + "paginainicial");
	}
	public void redirecionaPaginaVideos() {
		redireciona(pathProject + "videos");
	}
	public void redirecionaPaginaContato() {
		redireciona(pathProject + "contato");
	}
	public void redirecionaNossaMissao() {
		redireciona(pathProject + "nossamissao");
	}
	public void redirecionaNossaVisao() {
		redireciona(pathProject + "nossavisao");
	}
	public void redirecionaDesafios() {
		redireciona(pathProject + "desafios");
	}
	public void redirecionaCultoseReunioes() {
		redireciona(pathProject + "cultosereunioes");
	}
	public void redirecionaNoticias() {
		redireciona(pathProject + "noticias");
	}
	public void redirecionaIgrejasMbci() {
		redireciona(pathProject + "igrejasmbci");
	}
	public void redirecionaNoticia01() {
		redireciona(pathProject + "noticia01");
	}
	public void redirecionaNoticia02() {
		redireciona(pathProject + "noticia02");
	}
	public void redirecionaNoticia03() {
		redireciona(pathProject + "noticia03");
	}
	public void redirecionaNoticia04() {
		redireciona(pathProject + "noticia04");
	}
	public void redirecionaNoticia05() {
		redireciona(pathProject + "noticia05");
	}
	public void redirecionaNoticia06() {
		redireciona(pathProject + "noticia06");
	}
	
	
	
	
	private void redireciona(String url) {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(url);
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}
}
