package control;

import dao.ContatoDAO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.Contato;

@ManagedBean
@SessionScoped
public class ContatoCtrl {

    private Contato contato = new Contato();
    private ContatoDAO dao = new ContatoDAO();
    private List<Contato> lista = new ArrayList<>();

    public void adicionarMensagem(String titulo, String mensagem, FacesMessage.Severity tipoMSG) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(tipoMSG, titulo, mensagem);
        context.addMessage(null, message);
    }

    public void adicionar() {
        if (!contato.getNome().isEmpty() && !contato.getEmail().isEmpty() && !contato.getTelefone().isEmpty()) {
            if (dao.addContato(contato)) {
                // Criando a mensagem
                adicionarMensagem("Informação", "Contato salvo com sucesso!", FacesMessage.SEVERITY_INFO);
                //Criando novo contato
                contato = new Contato();
                //Atualizar a listagem de contato
                lista = dao.buscar();
            } else {
                adicionarMensagem("Atenção", "Ops! Erro ao tentar salvar contato.", FacesMessage.SEVERITY_ERROR);
            }
        } else {
            adicionarMensagem("Atenção", "Favor preencher todos os campos!", FacesMessage.SEVERITY_WARN);
        }
    }

    public void listar() {
        lista = dao.buscar();
        if (lista.isEmpty() || lista == null) {
            adicionarMensagem("Ops!", "Não foram encontrados contatos!", FacesMessage.SEVERITY_INFO);
        } else {
            adicionarMensagem("Aeee!", "Contatos listados com sucesso", FacesMessage.SEVERITY_INFO);
        }
    }

    public void editar(Contato c) {
        contato = c;
    }

    public void apagar(Contato c) {
        if (dao.delete(c.getId())) {
            adicionarMensagem("Atenção!", "Contatop excluído com sucesso!", FacesMessage.SEVERITY_INFO);
            //Criar um novo contato
            contato = new Contato();
            //Atualizar a listagem de contato
            lista = dao.buscar();
        } else {
            adicionarMensagem("Ops!", "Erro ao tentar excluir contato!", FacesMessage.SEVERITY_INFO);
        }
    }
    
    public Contato getContato() {
        return contato;
    }
    
    public void setContato(Contato contato) {
        this.contato = contato;
    }
    
    public List<Contato> getLista() {
        return lista;
    }
    
    public void setLista(List<Contato> lista) {
        this.lista = lista;
    }
    
}
