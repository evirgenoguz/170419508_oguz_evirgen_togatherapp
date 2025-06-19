# 📱 Togather Android Uygulaması

Togather, küçük grupların buluşmalarını organize etmelerine yardımcı olan bir Android uygulamasıdır. Bu dosya, uygulamanın Android tarafını derlemek ve geliştirme ortamını kurmak isteyen geliştiriciler için bir rehberdir.

---

## 🐳 Backend Servisini Yerelde Başlatma

Uygulamanın backend servisi Docker imajı üzerinden çalıştırılabilir. Aşağıdaki adımları izleyerek backend API'sini kendi local ortamınızda ayağa kaldırabilirsiniz.

### 1. Docker Kurulumu

Docker sisteminizde kurulu değilse [Docker resmi web sitesinden](https://www.docker.com/products/docker-desktop) indirip kurun.

### 2. Backend Image’ını Çekme

Öncelikle [Drive]("") linkinden compose.yaml ve .env dosyalarını indiriniz.
Bir klasorun içine aldıktan sonra terminali burada açınız.

Öncelikle aşağıdaki komut ile imajı docker hub üzerinden çekiniz.

```bash
docker image pull eksienes10/togather-be:latest  --platform linux/x86_64
```

### 3. Container’ı Başlatma
Terminal .env ve docker-compose.yaml'ın olduğu klasorde olduğundan emin olduktan sonra
aşağıdaki komut ile imajı ayağa kaldırınız.

```bash
docker compose up
```

Artık backend servisi `http://localhost:8080/swagger-ui/index.html#` adresinden swagger UI'a erişebilirsiniz.
Postgre Database için PgAdmin erişimine ise `http://localhost:5050` üzerinden erişebilirsiniz.

PgAdmin giriş için kullanıcı adı ve şifreyi docker-compose.yaml üzerinden erişebilir, değiştirebilirsiniz..

---

## 🧱 Proje Yapısı & MVI Mimarisi

Uygulama modern Android mimarilerini temel alarak **MVI (Model-View-Intent)** yapısıyla inşa edilmiştir. Kod yapısı modüler olarak ayrılmıştır.

### 📦 Modüller

```
:app                --> Uygulamanın giriş noktası (dependency ve yapılandırma içerir). Main Navigation buradan yapılır.
:core               --> Ortak kullanılan base classlar, util fonksiyonlar, tema yapıları
:domain             --> UseCase'ler, model tanımları ve repository interfaceleri
:data               --> Repository implementasyonları, API servisleri
:feature            --> ViewModel ve Ui Classlarını(Fragment, Adapters) içeren moduledur
:location           --> Location ile ilgili işlemlerin handle edildiği module
```

### 🔄 MVI Yapısı

Her feature modülü aşağıdaki bileşenleri içerir:

- `ViewModel`: UI durumlarını `State`, kullanıcı etkileşimlerini `Intent` olarak yönetir.
- `State`: Ekranın mevcut durumunu temsil eder.
- `Intent`: Kullanıcının yaptığı işlemleri temsil eder. (Projede `Action` olarak adlandırılmıştır)
- `Effect`: Tek seferlik UI olayları (örneğin Toast gösterimi, navigation) (Projede `Event` olarak adlandırılmıştır)
- `View`: XML + Fragment/Activity katmanı

---

## ⚙️ Geliştirme Ortamı

### Gereksinimler

- Android Studio Giraffe veya daha günceli
- JDK 17
- Kotlin DSL
- Gradle 8+
- Minimum SDK: 24
- Target SDK: 34

### Projeyi Başlatmak İçin

```bash
git clone https://github.com/evirgenoguz/ToGatherAndroid.git
```

Android Studio ile projeyi açın local.properties içerisine 
LOCAL_HOST={IP Adresiniz}
MAPS_API_KEY={Google Maps API Key} 
Eklemelerini yaptıktan sonra `:app` modülünü çalıştırarak uygulamayı test edebilirsiniz.

---

## 📌 Ek Bilgiler

- **Navigation**: Navigation Component + interface tabanlı yönlendirme yapısı
- **Görsel Yükleme**: Coil kullanılarak yapılır
- **Dependency Injection**: Hilt ile yapılandırılmıştır
- **Veri Katmanı**: Retrofit + Room DB + Firebase
- **Görsel Saklama**: Firebase Storage
- **Maps**: GoogleMaps kullanılmıştır.
- **Analytics-Crashlytics**: Firebase Analytics
- **Önbellek Yapısı**: Room DB

---

## 🧭 Modüller Arası Bağlantı Diyagramı

Aşağıdaki görsel, modüller arası bağımlılık ilişkilerini göstermektedir:

<img width="930" alt="moduleStructure" src="https://github.com/user-attachments/assets/eb088f9a-596a-49b8-acb4-eeb58d1ef9bd" />

## ✨ Katkıda Bulunmak

Katkıda bulunmak isterseniz, lütfen `dev` dalından fork alarak pull request gönderin.
