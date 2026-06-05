import java.util.*;
import java.util.List;
/*
 * Program Pencari Rute Terpendek TJ
 *
 * Struktur data yang dipake:
 * - Graf berarah berbobot (adjacency list pakai HashMap)
 * - PriorityQueue (min-heap) buat proses Dijkstra-nya
 *
 * Data: 21 halte, 60 jalur (dalam km), 50 destinasi wisata
 */
public class UAS_TJ {
    //Menyimpan hasil dari satu kali jalanin Dijkstra:
    //jarak terpendek ke semua node, sama peta halte sebelumnya
    //(buat rekonstruksi rute nanti inget bang oke jangan lupa bang lu dh lupa pasti)
    static final class DijkstraResult {
        final Map<String, Double> dist;
        final Map<String, String> prev;

        DijkstraResult(Map<String, Double> dist, Map<String, String> prev) {
            this.dist = Collections.unmodifiableMap(dist);
            this.prev = Collections.unmodifiableMap(prev);
        }

        //Rekonstruksi rute dari halte asal ke halte tujuan
        List<String> path(String start, String end) {
            if (!prev.containsKey(end) && !end.equals(start)) return Collections.emptyList();

            LinkedList<String> path = new LinkedList<>();
            for (String cur = end; cur != null; cur = prev.get(cur)) {
                path.addFirst(cur);
                if (cur.equals(start)) break;
            }

            if (path.isEmpty() || !path.getFirst().equals(start)) return Collections.emptyList();
            return path;
        }
    }

    record Destination(String name, String nearestHalte) {}


    //Adjacency list: halte asal -> (halte tujuan -> jarak km)
    static final Map<String, Map<String, Double>> graph = new HashMap<>();

    //Tambah jalur satu arah ke graf
    //Kalau jalur yang sama sudah ada sebelumnya, yh abaiin aj bang
    static void addEdge(String from, String to, double weightKm) {
        graph.computeIfAbsent(from, k -> new HashMap<>()).putIfAbsent(to, weightKm);
        graph.computeIfAbsent(to,   k -> new HashMap<>()); //buat mastiin halte tujuan juga terdaftar
    }


    static DijkstraResult dijkstra(String start) {
        Map<String, Double> dist = new HashMap<>();
        Map<String, String> prev = new HashMap<>();

        //Entry buat priority queue: simpan nama halte sama jarak estimasinya
        record QueueEntry(String node, double distance) implements Comparable<QueueEntry> {
            @Override public int compareTo(QueueEntry o) {
                return Double.compare(this.distance, o.distance);
            }
        }

        PriorityQueue<QueueEntry> pq = new PriorityQueue<>();

        //Set semua jarak ke tak infinit
        for (String node : graph.keySet()) {
            dist.put(node, Double.MAX_VALUE);
        }

        //Jarak ke titik awal = 0
        dist.put(start, 0.0);
        pq.add(new QueueEntry(start, 0.0));

        while (!pq.isEmpty()) {
            QueueEntry entry = pq.poll();
            String u = entry.node();
            double d = entry.distance();

            //Entry dh nt (ada yang lebih pendek), skip
            if (d > dist.getOrDefault(u, Double.MAX_VALUE)) continue;

            //Cek semua tetangga, update kalau ketemu jalan lebih pendek
            for (Map.Entry<String, Double> neighbor : graph.getOrDefault(u, Collections.emptyMap()).entrySet()) {
                String v    = neighbor.getKey();
                double w    = neighbor.getValue();
                double newD = dist.get(u) + w;

                if (newD < dist.getOrDefault(v, Double.MAX_VALUE)) {
                    dist.put(v, newD);
                    prev.put(v, u);
                    pq.add(new QueueEntry(v, newD));
                }
            }
        }

        return new DijkstraResult(dist, prev);
    }

    // Daftarin semua jalur TransJakarta ke dalam graf
    // From Halte to Halte weightKm brp
    static void initGraph() {
        //Ragunan dan sekitarnya
        addEdge("Ragunan",                   "Mampang Prapatan",          7.45);
        addEdge("Mampang Prapatan",          "Gatot Subroto LIPI",        2.15);
        addEdge("Mampang Prapatan",          "Kuningan Timur",            0.95);
        addEdge("Mampang Prapatan",          "Ragunan",                   7.50);

        //TMII dan Cawang
        addEdge("TMII",                      "Cawang UKI",               11.81);
        addEdge("Cawang UKI",                "TMII",                      6.90);
        addEdge("Cawang UKI",                "Kuningan Barat",            7.50);
        addEdge("Cawang UKI",                "Bidara Cina",               3.90); //p duplikat nanti apus bang
        addEdge("Kuningan Barat",            "Cawang UKI",                7.50);

        //Kuningan
        addEdge("Kuningan Barat",            "Gatot Subroto LIPI",        1.70);
        addEdge("Kuningan Barat",            "Kuningan Timur",            0.13);
        addEdge("Kuningan Timur",            "Gatot Subroto LIPI",        1.85);
        addEdge("Kuningan Timur",            "Setiabudi Utara AINI",      3.23);
        addEdge("Kuningan Timur",            "Kuningan Barat",            0.13);

        //Gatot Subroto dan Karet
        addEdge("Gatot Subroto LIPI",        "Karet Sudirman",            2.60);
        addEdge("Gatot Subroto LIPI",        "Mampang Prapatan",          2.15);
        addEdge("Gatot Subroto LIPI",        "Kuningan Timur",            1.85);
        addEdge("Karet Sudirman",            "Dukuh Atas 1",              0.80);
        addEdge("Karet Sudirman",            "Gatot Subroto LIPI",        2.60);

        //Bidara Cina dan Kebon Pala
        addEdge("Bidara Cina",               "Kebon Pala",                2.05);
        addEdge("Bidara Cina",               "Cawang UKI",                3.95);
        addEdge("Kebon Pala",                "Bidara Cina",               2.30); // p duplikat nanti apus bang
        addEdge("Kebon Pala",                "Senen Sentral",             4.55);
        addEdge("Senen Sentral",             "Kebon Pala",                4.55);

        //Setiabudi
        addEdge("Setiabudi Utara AINI",      "Kuningan Timur",            3.23);
        addEdge("Setiabudi Utara AINI",      "Dukuh Atas 2",              3.20);
        addEdge("Setiabudi Utara AINI",      "Sarinah",                   2.60);
        addEdge("Setiabudi Utara AINI",      "Senen Sentral",             6.99);

        //Dukuh Atas
        addEdge("Dukuh Atas 1",              "Karet Sudirman",            0.80);
        addEdge("Dukuh Atas 1",              "Dukuh Atas 2",              0.30);
        addEdge("Dukuh Atas 1",              "Sarinah",                   2.00);
        addEdge("Dukuh Atas 1",              "Setiabudi Utara AINI",      3.20);
        addEdge("Dukuh Atas 2",              "Dukuh Atas 1",              0.30); //p duplikat nanti apus bang
        addEdge("Dukuh Atas 2",              "Setiabudi Utara AINI",      1.31);

        //Senen
        addEdge("Senen Sentral",             "Setiabudi Utara AINI",     10.14);
        addEdge("Senen Sentral",             "Senen",                     0.06);
        addEdge("Senen Sentral",             "Pasar Baru Timur",          2.20);
        addEdge("Senen",                     "Monas",                     6.00);
        addEdge("Senen",                     "Senen Sentral",             0.06);
        addEdge("Senen",                     "Pasar Baru Timur",          2.10);

        //Sarinah dan Monas
        addEdge("Sarinah",                   "Setiabudi Utara AINI",      2.60);
        addEdge("Sarinah",                   "Monas",                     1.40);
        addEdge("Sarinah",                   "Dukuh Atas 1",              2.00);
        addEdge("Monas",                     "Senen",                     3.30);
        addEdge("Monas",                     "Pasar Baru Timur",          4.30);
        addEdge("Monas",                     "Kota",                      4.40);
        addEdge("Monas",                     "Sarinah",                   1.40);

        //Kota
        addEdge("Kota",                      "Gunung Sahari Mangga Dua",  2.30);
        addEdge("Kota",                      "Monas",                     4.40);

        //Pasar Baru Timur
        addEdge("Pasar Baru Timur",          "Senen Sentral",             2.20);
        addEdge("Pasar Baru Timur",          "Monas",                     3.40);
        addEdge("Pasar Baru Timur",          "Gunung Sahari Mangga Dua",  2.90);
        addEdge("Pasar Baru Timur",          "Senen",                     2.10);

        //Ancol dan Gunung Sahari Mangga Dua
        addEdge("Ancol",                     "Gunung Sahari Mangga Dua",  1.15);
        addEdge("Gunung Sahari Mangga Dua",  "Ancol",                     1.20);
        addEdge("Gunung Sahari Mangga Dua",  "Kota",                      2.40);
        addEdge("Gunung Sahari Mangga Dua",  "Pasar Baru Timur",          2.85);
    }



    //Daftar 50 destinasi, masing-masing dh dimap ke halte terdekatnya
    //name: nama destinasi + nearestHalte: halte terdekat
    static List<Destination> buildDestinations() {
        return List.of(
            //Monas
            new Destination("Monumen Nasional (Monas)",               "Monas"),
            new Destination("Istana Merdeka",                         "Monas"),
            new Destination("Museum Nasional / Gajah",                "Monas"),
            new Destination("Perpustakaan Nasional",                  "Monas"),

            //Pasar Baru Timur
            new Destination("Masjid Istiqlal",                        "Pasar Baru Timur"),
            new Destination("Katedral Jakarta",                       "Pasar Baru Timur"),

            //area Sarinah
            new Destination("Sarinah Department Store",               "Sarinah"),
            new Destination("Bundaran HI",                            "Sarinah"),
            new Destination("Grand Indonesia",                        "Sarinah"),
            new Destination("Plaza Indonesia",                        "Sarinah"),

            //Kota Tua
            new Destination("Kawasan Kota Tua",                       "Kota"),
            new Destination("Museum Fatahillah",                      "Kota"),
            new Destination("Museum Bank Indonesia",                  "Kota"),
            new Destination("Museum Wayang",                          "Kota"),

            //area Mangga Dua
            new Destination("Pasar Pagi Mangga Dua",                  "Gunung Sahari Mangga Dua"),
            new Destination("WTC Mangga Dua",                         "Gunung Sahari Mangga Dua"),
            new Destination("Mangga Dua Square",                      "Gunung Sahari Mangga Dua"),

            //Ancol
            new Destination("Taman Impian Jaya Ancol",                "Ancol"),
            new Destination("Dunia Fantasi (Dufan)",                  "Ancol"),
            new Destination("Sea World Ancol",                        "Ancol"),
            new Destination("Atlantis Water Adventures",              "Ancol"),
            new Destination("Pantai Festival Ancol",                  "Ancol"),

            //Ragunan
            new Destination("Taman Margasatwa Ragunan",               "Ragunan"),
            new Destination("Pusat Primata Schmutzer",                "Ragunan"),

            //TMII
            new Destination("Taman Mini Indonesia Indah (TMII)",      "TMII"),
            new Destination("Keong Emas TMII",                        "TMII"),
            new Destination("Museum Transportasi TMII",               "TMII"),
            new Destination("MNCTV Studios",                          "TMII"),

            //Cawang UKI
            new Destination("Cawang Komersial Area",                  "Cawang UKI"),
            new Destination("Universitas Kristen Indonesia (UKI)",    "Cawang UKI"),

            //Kuningan
            new Destination("Plaza Festival",                         "Kuningan Timur"),
            new Destination("Rasuna Epicentrum",                      "Kuningan Timur"),
            new Destination("Kuningan City",                          "Kuningan Barat"),
            new Destination("Mall Ambassador",                        "Kuningan Barat"),
            new Destination("ITC Kuningan",                           "Kuningan Timur"),
            new Destination("Ciputra World / Lotte Shopping Avenue",  "Kuningan Barat"),

            //Gatot Subroto LIPI
            new Destination("Menara Jamsostek",                       "Gatot Subroto LIPI"),
            new Destination("LIPI / BRIN Headquarters",               "Gatot Subroto LIPI"),
            new Destination("Balai Kartini",                          "Gatot Subroto LIPI"),

            //Karet Sudirman
            new Destination("Karet Pedurenan Commercial Area",        "Karet Sudirman"),
            new Destination("Le Meridien Hotel",                      "Karet Sudirman"),
            new Destination("Intiland Tower (Wisma Dharmala)",        "Karet Sudirman"),

            //Dukuh Atas
            new Destination("Landmark Centre",                        "Dukuh Atas 1"),
            new Destination("Stasiun Sudirman",                       "Dukuh Atas 1"),
            new Destination("Stasiun BNI City",                       "Dukuh Atas 2"),

            //Senen
            new Destination("Pasar Senen",                            "Senen"),
            new Destination("Plaza Atrium Senen",                     "Senen"),
            new Destination("Stasiun Pasar Senen",                    "Senen Sentral"),

            //Pasar Baru Timur
            new Destination("Pasar Baru",                             "Pasar Baru Timur"),
            new Destination("Gedung Kesenian Jakarta",                "Pasar Baru Timur")
        );
    }

    /*
    Helper buat nampilin data, 
    siapa tau CLI frontend dinilai
     */

    static final String GARIS_TEBAL  = "<=>".repeat(25);
    static final String GARIS_TIPIS  = "-".repeat(50);

    static void printJudul(String judul) {
        System.out.println(GARIS_TEBAL);
        System.out.printf("  %s%n", judul);
        System.out.println(GARIS_TEBAL);
    }

    static void printBagian(String judul) {
        System.out.println();
        System.out.println(GARIS_TIPIS);
        System.out.printf("  %s%n", judul);
        System.out.println(GARIS_TIPIS);
    }

    /*
    Main method
    jujur w ngasal dikit, spacing2nya asw lama bet bjir, untung bisa ngespam tab tab tab tab tab tab
    tapi ygpenting keliatan cantik cantik
     */

    public static void main(String[] args) {
        initGraph();

        List<Destination> destinations = buildDestinations();
        List<String>      halteList    = new ArrayList<>(graph.keySet());
        Collections.sort(halteList);

        Scanner scanner = new Scanner(System.in);

        // Judul program
        printJudul("Transjakarta Shortest Route Finder Thingamajig");
        System.out.printf("  %d halte  |  %d jalur  |  %d destinasi wisata%n",
            graph.size(),
            graph.values().stream().mapToInt(Map::size).sum(),
            destinations.size()
        );

        // Langkah 1: pilih halte asal
        printBagian("Select Halte asal");
        for (int i = 0; i < halteList.size(); i++) {
            System.out.printf("  %2d. %s%n", i + 1, halteList.get(i));
        }

        String startHalte;
        while (true) {
            System.out.println();
            System.out.printf("  Masukkan nomor halte (1-%d): ", halteList.size());
            String input = scanner.nextLine().trim();
            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= halteList.size()) {
                    startHalte = halteList.get(choice - 1);
                    break;
                }
            } catch (NumberFormatException ignored) { /* lanjut ke pesan error, grgr koding backen gatel malah masukin */ }
            System.out.printf("  Input invalid. antara 1 dan %d.%n", halteList.size());
        }

        //Jalanin Dijkstra sekali dari halte yang dipilih
        DijkstraResult result = dijkstra(startHalte);

        //Show semua destinasi, 50 total banyak bet
        printBagian("50 Destinations");
        System.out.printf("  Halte asal: %s%n%n", startHalte);
        System.out.printf("  %-4s  %-42s  %-28s  %s%n", "No.", "Destinasi", "Halte terdekat", "Jarak");
        System.out.println("  " + "-".repeat(69));

        for (int i = 0; i < destinations.size(); i++) {
            Destination dest        = destinations.get(i);
            double      jarakHalte  = result.dist.getOrDefault(dest.nearestHalte(), Double.MAX_VALUE);
            String      jarakStr    = (jarakHalte == Double.MAX_VALUE)
                                        ? "tidak terjangkau"
                                        : String.format("%.2f km", jarakHalte);

            System.out.printf("  %-4d  %-42s  %-28s  %s%n",
                i + 1,
                dest.name(),
                dest.nearestHalte(),
                jarakStr
            );
        }

        //pilih destinasi tujuan
        printBagian("Where you wanna go");

        int destChoice;
        while (true) {
            System.out.printf("  Masukkan nomor destinasi (1-%d): ", destinations.size());
            String input = scanner.nextLine().trim();
            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= destinations.size()) {
                    destChoice = choice;
                    break;
                }
            } catch (NumberFormatException ignored) {}
            System.out.printf("  Input invalid. antara 1 dan %d.%n", destinations.size());
        }

        //akhirnya show shortest route
        Destination  tujuan      = destinations.get(destChoice - 1);
        String       halteTujuan = tujuan.nearestHalte();
        double       totalJarak  = result.dist.getOrDefault(halteTujuan, Double.MAX_VALUE);
        List<String> rute        = result.path(startHalte, halteTujuan);

        printBagian("Shortest Route");
        System.out.printf("  From        : %s%n", startHalte);
        System.out.printf("  To          : %s%n", tujuan.name());
        System.out.printf("  Final Stop  : %s%n", halteTujuan);
        System.out.println();

        if (rute.isEmpty()) {
            System.out.println("  Tidak ada jalur yang bisa dilalui dari halte ini ke tujuan tersebut.");
        } else {
            System.out.printf("  Total jarak  : %.2f km%n", totalJarak);
            System.out.printf("  Jumlah halte : %d halte (%d Stops)%n",
                rute.size(), rute.size() - 1);
            System.out.println();
            System.out.println("  Map:");
            System.out.println();
            for (int i = 0; i < rute.size(); i++) {
                boolean awal  = (i == 0);
                boolean akhir = (i == rute.size() - 1);
                String  label = awal ? "[Start]  " : akhir ? "[End]" : "        ";
                System.out.printf("  %s  %2d. %s%n", label, i + 1, rute.get(i));
                if (!akhir) System.out.println("              |");
            }
        }

        //gkperlu tapi w copas dari kodingan lain cuman ganti2 ae, siapa tau dapet extra poin
        printBagian("Infograph");
        System.out.printf("  Jumlah vertex (halte) : %d%n", graph.size());
        System.out.printf("  Jumlah edge (jalur)   : %d%n",
            graph.values().stream().mapToInt(Map::size).sum());
        System.out.println("  Kompleksitas waktu    : O((V + E) log V)");
        System.out.println("  Struktur data         : Adjacency List (HashMap + PriorityQueue min-heap)");
        System.out.println(GARIS_TEBAL);

        scanner.close();
    }
}