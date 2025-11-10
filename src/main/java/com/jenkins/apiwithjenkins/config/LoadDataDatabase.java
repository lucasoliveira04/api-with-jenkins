package com.jenkins.apiwithjenkins.config;

import com.jenkins.apiwithjenkins.entity.Address;
import com.jenkins.apiwithjenkins.entity.Users;
import com.jenkins.apiwithjenkins.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Profile({"test"})
@Component
@RequiredArgsConstructor
public class LoadDataDatabase implements CommandLineRunner {

    private final UserRepository userRepository;
    private final Random random = new Random();
    private final Set<String> usedPhones = new HashSet<>();

    private String generateUniquePhone() {
        String phone;
        do {
            phone = "55" + (900000000 + random.nextInt(100000000));
        } while (!usedPhones.add(phone));
        return phone;
    }

    @Override
    public void run(String... args) {
        userRepository.findAll().forEach(user -> usedPhones.add(user.getPhone()));

        int totalUsuarios = 10000;
        List<String> nomes = Arrays.asList("Lucas", "Ana", "Pedro", "Mariana", "Carlos", "Fernanda", "João", "Beatriz", "Ricardo", "Camila", "Vinícius", "Aline", "André", "Larissa", "Gustavo", "Patrícia", "Felipe", "Bruna", "Rafael", "Juliana");
        List<String> sobrenomes = Arrays.asList("Oliveira", "Souza", "Silva", "Santos", "Ferreira", "Costa", "Pereira", "Rodrigues", "Martins", "Almeida", "Gomes", "Lima", "Barbosa", "Azevedo");
        List<String> cidades = Arrays.asList("São Paulo", "Rio de Janeiro", "Curitiba", "Florianópolis", "Belo Horizonte", "Recife", "Porto Alegre", "Salvador", "Brasília", "Campinas");
        List<String> estados = Arrays.asList("SP", "RJ", "PR", "SC", "MG", "PE", "RS", "BA", "DF");
        List<String> ruas = Arrays.asList("Rua das Flores", "Avenida Brasil", "Rua São José", "Rua das Acácias", "Travessa dos Pinheiros", "Rua Dom Pedro II", "Rua XV de Novembro", "Rua da Paz", "Avenida Paulista", "Rua dos Andradas");
        List<String> paises = Collections.singletonList("Brasil");

        List<Users> users = new ArrayList<>();

        for (int i = 1; i <= totalUsuarios; i++) {
            String nomeCompleto = nomes.get(random.nextInt(nomes.size())) + " " + sobrenomes.get(random.nextInt(sobrenomes.size()));

            Users user = Users.builder()
                    .name(nomeCompleto)
                    .email(nomeCompleto.toLowerCase().replace(" ", ".")
                            + i
                            + UUID.randomUUID().toString().substring(0, 6)
                            + "@teste.com")
                    .cpf(String.format("%011d", random.nextInt(999999999)))
                    .phone(generateUniquePhone())
                    .birthDate(LocalDate.of(
                            1980 + random.nextInt(25),
                            1 + random.nextInt(12),
                            1 + random.nextInt(28)))
                    .build();

            List<Address> addresses = new ArrayList<>();
            int addressCount = 1 + random.nextInt(3);

            for (int j = 1; j <= addressCount; j++) {
                Address address = Address.builder()
                        .street(ruas.get(random.nextInt(ruas.size())) + ", nº " + (10 + random.nextInt(990)))
                        .city(cidades.get(random.nextInt(cidades.size())))
                        .state(estados.get(random.nextInt(estados.size())))
                        .zipCode(String.format("%05d-%03d", random.nextInt(99999), random.nextInt(999)))
                        .country(paises.get(0))
                        .user(user)
                        .build();
                addresses.add(address);
            }

            user.setAddress(addresses);
            users.add(user);
        }

        userRepository.saveAll(users);
        System.out.printf("%d usuários com dados realistas e endereços aleatórios foram inseridos!%n", totalUsuarios);
    }
}
