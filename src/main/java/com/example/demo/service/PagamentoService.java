package com.example.demo.service;

import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.BackUrls;
import com.mercadopago.resources.datastructures.preference.Item;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;

import com.example.demo.model.Produto;

@Service
public class PagamentoService {

    @Value("${mercadopago.access.token}")
    private String accessToken;

    public String criarPreferencia(List<Produto> produtos) throws MPException {
        MercadoPago.SDK.setAccessToken(accessToken);

        Preference preference = new Preference();

        // Configurar as back_urls
        BackUrls backUrls = new BackUrls(
            "http://localhost:3000/pagamento/sucesso",   // success
            "http://localhost:3000/pagamento/pendente",  // pending
            "http://localhost:3000/pagamento/erro"       // failure
        );
        preference.setBackUrls(backUrls);

        for (Produto produto : produtos) {
            Item item = new Item()
                .setTitle(produto.getNome())
                .setQuantity(produto.getQuantidade())
                .setUnitPrice((float) produto.getPreco());
            preference.appendItem(item);
        }

        preference.save();
        return preference.getInitPoint(); // URL para o pagamento
    }
}
