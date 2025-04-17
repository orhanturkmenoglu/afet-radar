# Afet-Radar – Depremleri Anlık Takip Eden Uygulama 🚨

Afet-Radar, kullanıcıların geçmiş ve güncel depremleri görüntüleyebileceği, belirli büyüklüğün üzerindeki depremler için uyarı alabileceği bir sistemdir. Gerçek zamanlı veri akışı sayesinde olası risklerin erkenden fark edilmesini hedefler.

## Projenin Amacı 📌

Afet-Radar, toplumsal güvenliği artırmayı ve afet bilincini yaymayı amaçlayan bir uygulamadır. Kullanıcılar, AFAD'dan alınan verilerle, Türkiye genelinde meydana gelen depremleri takip edebilir, belirli büyüklüğün üzerindeki depremler için bildirim alabilirler.

### Kullanıcı Özellikleri:
- **Geçmiş ve Güncel Depremler:** Kullanıcılar, geçmiş ve güncel depremleri görüntüleyebilir.
- **Uyarı Sistemi:** Belirli büyüklükteki depremler için kullanıcıya bildirim gönderme.
- **Özelleştirilmiş Sorgular:** Lokasyona, derinliğe veya büyüklüğe göre sorgulama yapma.
- **Kullanıcı Bazlı Tercihler:** Kullanıcı bazlı tercihleriyle özelleştirilen uyarı sistemi.
- **Bildirim Gönderme:** Belirli bir büyüklüğün üzerindeki depremler için kullanıcıya anlık bildirim gönderilir.
- **WebSocket ile Gerçek Zamanlı Bildirim:** Uygulama, belirlenen eşik değerini geçen depremler için anlık bildirimleri WebSocket üzerinden iletir.


## Projede Kullandığım Teknolojiler 🔧

- **Spring Boot:** Güçlü ve ölçeklenebilir backend yapısı.
- **RestTemplate:** AFAD’dan anlık veri çekimi.
- **@Scheduled Anotasyonu:** 60 saniye içerisinde verileri otomatik güncelleme.
- **DTO & Mapper Yapısı:** Veri dönüşümünde temizlik ve düzen.
- **Projection:** Performansı artırmak için hafif sorgular.
- **Lombok:** Kodun daha sade, okunabilir ve bakımının kolay olmasını sağlar.
- **Modern Java Özellikleri:** Optional, stream, filter gibi özellikler ile temiz kod yaklaşımı.

## Yakında Gelecek Özellikler 🚀

- **Özelleştirilmiş Sorgular:** Lokasyona, derinliğe veya büyüklüğe göre özelleştirilmiş sorgular.
- **Kullanıcı Tercihlerine Göre Uyarı Sistemi:** Kullanıcı bazlı tercihlere göre kişiselleştirilmiş uyarılar.
- **Swagger UI Entegrasyonu:** API dökümantasyonu ve test kolaylığı.

## Proje Nasıl Çalıştırılır?

1. Bu repoyu klonlayın:
   ```bash
   git clone https://github.com/orhanturkmenoglu/afet-radar.git
2. Spring Boot uygulamanızı başlatın:
   ```bash
   mvn spring-boot:run

Katkıda Bulunma 💡
Afet-Radar açık kaynak bir projedir ve katkılara açıktır! Eğer projeye katkı sağlamak isterseniz, lütfen aşağıdaki adımları izleyin:

Bu repoyu fork edin.

Değişikliklerinizi yapın.

Pull request gönderin.

Her türlü geri bildirim ve öneri çok değerli olacaktır!

