# Risoluzione Errore 401 Unauthorized

## ❌ Problema

```
Error: Could not transfer artifact it.auth.security:auth-security-core:pom:0.0.1-SNAPSHOT 
from/to github (https://maven.pkg.github.com/AntonioBasileo/auth-security-core): 
status code: 401, reason phrase: Unauthorized (401)
```

## 🔍 Causa

Il problema era una **mancata corrispondenza tra gli ID** nel `settings.xml`:

### Configurazione Errata (prima):
```xml
<servers>
  <server>
    <id>personal</id>  ❌ ID errato
    <username>${env.GITHUB_ACTOR}</username>
    <password>${env.GITHUB_TOKEN}</password>
  </server>
</servers>

<repositories>
  <repository>
    <id>github</id>  ❌ ID diverso dal server
    <url>https://maven.pkg.github.com/AntonioBasileo/auth-security-core</url>
  </repository>
</repositories>
```

**Risultato**: Maven non trovava le credenziali per il repository con id `github` perché il server aveva id `personal`.

## ✅ Soluzione

### Configurazione Corretta (dopo):
```xml
<servers>
  <server>
    <id>github</id>  ✅ Corrisponde al repository
    <username>${env.GITHUB_ACTOR}</username>
    <password>${env.GITHUB_TOKEN}</password>
  </server>
</servers>

<repositories>
  <repository>
    <id>github</id>  ✅ Stesso ID del server
    <url>https://maven.pkg.github.com/AntonioBasileo/auth-security-core</url>
  </repository>
</repositories>
```

**Risultato**: Maven ora usa le credenziali corrette quando accede al repository `github`.

## 📝 Modifiche Apportate

### 1. Corretto l'ID del server
- ❌ Prima: `<id>personal</id>`
- ✅ Dopo: `<id>github</id>`

### 2. Corretto l'ID del profilo
- ❌ Prima: `<id>personal</id>`
- ✅ Dopo: `<id>github</id>`

### 3. Corretto l'activeProfile
- ❌ Prima: `<activeProfile>personal</activeProfile>`
- ✅ Dopo: `<activeProfile>github</activeProfile>`

### 4. Corretto typo nel comando Maven
- ❌ Prima: `mvn clean package -DskipTestsì`
- ✅ Dopo: `mvn clean package -DskipTests`

### 5. Aggiunte variabili d'ambiente mancanti
Aggiunto `env` allo step "Publish to GitHub Packages"

## 🔑 Regola Importante di Maven

**L'ID del server deve corrispondere all'ID del repository!**

Quando Maven deve accedere a un repository:
1. Guarda l'ID del repository (es. `github`)
2. Cerca un server con lo stesso ID in `<servers>`
3. Usa le credenziali di quel server per autenticarsi

Se gli ID non corrispondono → Maven non trova le credenziali → Errore 401 Unauthorized

## 🎯 Come Funziona Ora

1. **Maven legge il settings.xml** generato dalla pipeline
2. **Trova il repository con id `github`** nell'URL `https://maven.pkg.github.com/AntonioBasileo/auth-security-core`
3. **Cerca il server con id `github`** nella sezione `<servers>`
4. **Usa le credenziali configurate** (GITHUB_ACTOR e GITHUB_TOKEN)
5. **Si autentica correttamente** e scarica le dipendenze

## ⚠️ Nota Finale

Perché la pipeline funzioni completamente, assicurati che:
1. ✅ Il package `auth-security-core` sia pubblicato su GitHub Packages
2. ✅ Il package sia accessibile pubblicamente o con le credenziali fornite
3. ✅ Il workflow abbia i permessi `packages: write` (già configurato)

Ora il problema di autenticazione è risolto! 🎉

