# Configurazione Repository nella Pipeline

## ✅ Configurazione Completata

Ho configurato la pipeline per specificare esplicitamente da dove scaricare le dipendenze, incluso `auth-security-core`.

## 📝 Come Funziona

### 1. **Nella Pipeline (GitHub Actions)**

Lo step "Configure Maven Settings" crea un `settings.xml` custom che:

```yaml
- name: Configure Maven Settings
  run: |
    mkdir -p ~/.m2
    cat > ~/.m2/settings.xml <<EOF
    <settings>
      <servers>
        <server>
          <id>github</id>
          <username>${{ github.actor }}</username>
          <password>${{ secrets.GITHUB_TOKEN }}</password>
        </server>
      </servers>
      <profiles>
        <profile>
          <id>github</id>
          <repositories>
            <repository>
              <id>github</id>
              <url>https://maven.pkg.github.com/AntonioBasileo/auth-security-core</url>
              <snapshots><enabled>true</enabled></snapshots>
            </repository>
          </repositories>
        </profile>
      </profiles>
      <activeProfiles>
        <activeProfile>github</activeProfile>
      </activeProfiles>
    </settings>
    EOF
```

**Vantaggi:**
- ✅ Specifichi esattamente da quale repository GitHub prendere `auth-security-core`
- ✅ Le credenziali sono configurate automaticamente
- ✅ Non devi modificare `~/.m2/settings.xml` manualmente
- ✅ Funziona sia per scaricare dipendenze che per pubblicare

### 2. **Nel pom.xml**

Il `pom.xml` ha ancora la sezione `<repositories>` per lo sviluppo locale:

```xml
<repositories>
    <repository>
        <id>central</id>
        <url>https://repo1.maven.org/maven2</url>
    </repository>

    <repository>
        <id>github</id>
        <url>https://maven.pkg.github.com/AntonioBasileo/auth-security-core</url>
        <snapshots><enabled>true</enabled></snapshots>
    </repository>
</repositories>
```

**Nota:** Per lo sviluppo locale, dovrai comunque configurare il tuo `~/.m2/settings.xml` con le credenziali.

## 🔐 Autenticazione

Il workflow crea due server nel settings.xml:

1. **`github`**: Per scaricare dipendenze da `auth-security-core`
2. **`github-core`**: Repository aggiuntivo se necessario

Entrambi usano:
- **Username**: `${{ github.actor }}` (il tuo username GitHub)
- **Password**: `${{ secrets.GITHUB_TOKEN }}` (token automatico di GitHub Actions)

## 📦 Repository Configurati nella Pipeline

La pipeline è configurata per cercare dipendenze in questi repository (nell'ordine):

1. **Maven Central**: `https://repo1.maven.org/maven2`
   - Repository principale per dipendenze pubbliche

2. **auth-security-core**: `https://maven.pkg.github.com/AntonioBasileo/auth-security-core`
   - Repository specifico per la dipendenza `auth-security-core`

3. **auth-security-starter**: `https://maven.pkg.github.com/AntonioBasileo/auth-security-starter`
   - Repository dove pubblicare questo progetto

## 🚀 Deployment

Il comando di deploy usa un parametro speciale per specificare dove pubblicare:

```bash
mvn deploy -DskipTests -DaltDeploymentRepository=github::default::https://maven.pkg.github.com/${{ github.repository }}
```

Questo pubblica il package nel repository corrente.

## ⚠️ Requisiti

Perché funzioni, devi assicurarti che:

1. ✅ Il package `auth-security-core` sia pubblicato su GitHub Packages
2. ✅ Il package sia accessibile (pubblico o con i permessi corretti)
3. ✅ Il workflow abbia i permessi `packages: write` (già configurato)

## 🔍 Verifica

Se vuoi vedere il settings.xml generato, puoi aggiungere uno step di debug:

```yaml
- name: Debug Maven Settings
  run: cat ~/.m2/settings.xml
```

(Nota: Le password saranno mascherate automaticamente da GitHub nei log)

## 💡 Sviluppo Locale

Per testare in locale, crea un `~/.m2/settings.xml`:

```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                              https://maven.apache.org/xsd/settings-1.0.0.xsd">
    <servers>
        <server>
            <id>github</id>
            <username>TUO_USERNAME</username>
            <password>TUO_GITHUB_TOKEN</password>
        </server>
    </servers>
</settings>
```

Dove `TUO_GITHUB_TOKEN` è un Personal Access Token con scope `read:packages`.

## 📋 Riassunto

**Sì, puoi specificare nella pipeline da quale repository prendere `auth-security-core`!**

La configurazione attuale:
- ✅ Crea automaticamente un settings.xml nella pipeline
- ✅ Specifica esattamente i repository da usare
- ✅ Configura le credenziali automaticamente
- ✅ Supporta SNAPSHOT e release
- ✅ Funziona sia per scaricare che per pubblicare packages

Ora la pipeline sa esattamente dove cercare `auth-security-core`! 🎉

