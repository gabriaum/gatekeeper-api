package com.gabriaum.gatekeeper;

import com.gabriaum.gatekeeper.infra.service.CryptographyService;
import com.gabriaum.gatekeeper.user.GateUser;
import com.gabriaum.gatekeeper.user.enums.GateUserRole;
import com.gabriaum.gatekeeper.user.repository.GateUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.lang.NonNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class GateKeeperAdminUser implements CommandLineRunner {
    private final GateUserRepository repository;
    private final CryptographyService cryptographyService;

    @Value("${ADMIN_CREATE:false}")
    private boolean createAdmin;

    @Value("${ADMIN_CPF:}")
    private String adminCpf;

    @Value("${ADMIN_EMAIL:}")
    private String adminEmail;

    @Value("${ADMIN_PASSWORD:}")
    private String adminPassword;

    @Override
    public void run(@NonNull String... args) {
        if (!createAdmin) {
            log.debug("Default admin creation is disabled (ADMIN_CREATE=false)");
            return;
        }

        if (repository.existsByRole(GateUserRole.ADMIN)) {
            log.debug("Admin user already exists, skipping default admin creation");
            return;
        }

        if (adminCpf == null || adminCpf.isBlank() || adminEmail == null || adminEmail.isBlank() || adminPassword == null || adminPassword.isBlank()) {
            throw new IllegalStateException("ADMIN_CREATE=true requires ADMIN_CPF, ADMIN_EMAIL and ADMIN_PASSWORD to be provided.");
        }

        GateUser gateUser = new GateUser();
        gateUser.setCpf(adminCpf);
        gateUser.setEmail(adminEmail);
        gateUser.setRole(GateUserRole.ADMIN);
        gateUser.setPassword(cryptographyService.encryptPassword(adminPassword));

        repository.save(gateUser);

        log.info("O usuário administrativo foi criado com sucesso.");
        log.info("");
        log.info("Usuário: {}", gateUser.getUsername());
        log.info("Senha: {}", "[HIDDEN]");
        log.info("");
        log.warn("[!] Altere as credenciais assim que possível!");
    }
}