package it.auth.security.starter.repository;

import it.auth.security.core.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interfaccia provider per l'accesso ai dati degli utenti.
 * <p>
 * Questa interfaccia deve essere implementata dal progetto che utilizza la libreria
 * per fornire l'accesso al repository degli utenti.
 * </p>
 *
 * @author Antonio Basileo
 */
@Repository
public interface UserRepositoryProvider extends JpaRepository<AppUser, String> {

    /**
     * Cerca un utente per username.
     *
     * @param username lo username dell'utente
     * @return Optional contenente l'utente se trovato
     */
    Optional<AppUser> findByUsername(String username);

    /**
     * Verifica se esiste un utente con lo username specificato.
     *
     * @param username lo username da verificare
     * @return true se l'utente esiste, false altrimenti
     */
    boolean existsByUsername(String username);
}

