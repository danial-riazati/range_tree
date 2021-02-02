import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

class node {
    Double x;
    Double y;

    node(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    node(Double x) {
        this.x = x;
        this.y = null;
    }
}

class X_tree {
    node head;
    X_tree left, right;
    Y_tree linked;
    Integer minIndex, maxIndex;
    Double biggest;

    X_tree(node head) {
        this.head = head;
        this.left = null;
        this.right = null;
        linked = null;
        minIndex = -1;
        maxIndex = -1;
        biggest = head.x;
    }

    X_tree(node head, X_tree left, X_tree right) {
        this.head = head;
        this.left = left;
        this.right = right;
        linked = null;
        minIndex = -1;
        maxIndex = -1;
        biggest = right.biggest;
    }
}

class Y_tree {
    node head;
    Y_tree left, right;
    Double biggest;

    Y_tree(node head) {
        this.head = head;
        this.left = null;
        this.right = null;
        biggest = head.y;
    }

    Y_tree(node head, Y_tree left, Y_tree right) {
        this.head = head;
        this.left = left;
        this.right = right;
        biggest = right.biggest;
    }


}

class RangeTree {
    static node[] nodes;
    ArrayList<node> f;

    RangeTree(node[] nodes) {
        RangeTree.nodes = nodes;
        f = new ArrayList<>();
    }

    public void x_find(X_tree h, Double x1, Double y1, Double x2, Double y2) {
        if (h == null) {
            return;
        }
        if (h.right == null) {

            if ((h.head.x >= x1) && (h.head.x <= x2)) {
                if (h.head.y >= y1 && h.head.y <= y2) {
                    f.add(h.head);
                    return;
                }
            }

        }
        if (h.head.x < x1) {

            x_find(h.right, x1, y1, x2, y2);
        } else if (h.head.x >= x2) {

            x_find(h.left, x1, y1, x2, y2);
        } else {
            if (nodes[h.minIndex].x >= x1 && nodes[h.maxIndex].x <= x2) {
                y_find(h.linked, y1, y2);
            } else {

                x_find(h.left, x1, y1, x2, y2);
                x_find(h.right, x1, y1, x2, y2);
            }
        }
    }

    public void y_find(Y_tree h, Double y1, Double y2) {
        if (h == null) {
            return;
        }
        if (h.right == null) {

            if (h.head.y >= y1 && h.head.y <= y2) {
                f.add(h.head);
                return;
            }

        }
        if (h.head.y < y1) {

            y_find(h.right, y1, y2);
        } else if (h.head.y >= y2) {

            y_find(h.left, y1, y2);
        } else {
            y_find(h.left, y1, y2);
            y_find(h.right, y1, y2);
        }
    }

    public X_tree x_make_tree(node[] arr, int m, int n) {
        X_tree[] tmp = new X_tree[n];
        int size = 0;
        for (int i = m; i < n; i += 2) {
            if (i != n - 1) {
                X_tree tmp1 = new X_tree(arr[i]);
                tmp1.minIndex = i;
                tmp1.maxIndex = i;
                X_tree tmp2 = new X_tree(arr[i + 1]);
                tmp2.minIndex = i + 1;
                tmp2.maxIndex = i + 1;
                X_tree c;
                c = new X_tree(new node(tmp1.biggest, null), tmp1, tmp2);
                c.minIndex = tmp1.minIndex;
                c.maxIndex = tmp2.maxIndex;
                c.linked = y_make_tree(arr, c.minIndex, c.maxIndex + 1);
                tmp[size++] = c;

            } else {
                X_tree c;
                c = new X_tree(arr[i]);
                c.minIndex = i;
                c.maxIndex = i;
                tmp[size++] = c;
            }
        }
        X_tree[] tmp2 = new X_tree[n];
        int size2 = 0;
        while (size != 1) {
            for (int i = 0; i < size; i += 2) {
                if (i != size - 1) {
                    X_tree a = tmp[i];
                    X_tree b = tmp[i + 1];
                    X_tree c;
                    c = new X_tree(new node(a.biggest, null), a, b);
                    c.minIndex = a.minIndex;
                    c.maxIndex = b.maxIndex;
                    c.linked = y_make_tree(arr, c.minIndex, c.maxIndex + 1);
                    tmp2[size2++] = c;
                } else {
                    tmp2[size2++] = tmp[i];
                }
            }
            X_tree[] x = tmp;
            tmp = tmp2;
            size = size2;
            tmp2 = x;
            size2 = 0;
        }
        return tmp[0];
    }

    public Y_tree y_make_tree(node[] a, int m, int n) {
        node[] arr = new node[n - m];
        System.arraycopy(a, m, arr, 0, n - m);
        Arrays.sort(arr, test.y_sort);
        Y_tree[] tmp = new Y_tree[n];
        int size = 0;
        for (int i = 0; i < n - m; i += 2) {
            if (i != n - m - 1) {
                Y_tree tmp1 = new Y_tree(arr[i]);
                Y_tree tmp2 = new Y_tree(arr[i + 1]);
                tmp[size++] = new Y_tree(new node(null, tmp1.biggest), tmp1, tmp2);

            } else {
                tmp[size++] = new Y_tree(arr[i]);
            }
        }
        Y_tree[] tmp2 = new Y_tree[n];
        int size2 = 0;
        while (size != 1) {
            for (int i = 0; i < size; i += 2) {
                if (i != size - 1) {
                    tmp2[size2++] = new Y_tree(new node(null, tmp[i].biggest), tmp[i], tmp[i + 1]);
                } else {
                    tmp2[size2++] = tmp[i];
                }
            }
            Y_tree[] x = tmp;
            tmp = tmp2;
            size = size2;
            tmp2 = x;
            size2 = 0;
        }
        return tmp[0];
    }


}

class FastReader {
    BufferedReader br;
    StringTokenizer st;

    public FastReader() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    String next() {
        while (st == null || !st.hasMoreElements()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return st.nextToken();
    }

    int nextInt() {
        return Integer.parseInt(next());
    }

    double nextDouble() {
        return Double.parseDouble(next());
    }

}

public class test {
    public static Comparator<node> x_sort = (o1, o2) -> {
        if (o1.x > o2.x)
            return 1;
        else if (o1.x < o2.x)
            return -1;
        else if (o1.y > o2.y)
            return 1;
        else if (o1.y < o2.y)
            return -1;
        return 0;

    };
    public static Comparator<node> y_sort = (o1, o2) -> {
        if (o1.y > o2.y)
            return 1;
        else if (o1.y < o2.y)
            return -1;
        else if (o1.x > o2.x)
            return 1;
        else if (o1.x < o2.x)
            return -1;
        return 0;
    };

    public static void main(String[] args) {
        FastReader f = new FastReader();
        int n = f.nextInt();
        node[] nodes = new node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new node(f.nextDouble());
        }
        for (int i = 0; i < n; i++) {
            nodes[i].y = f.nextDouble();
        }
        Arrays.sort(nodes, x_sort);
        RangeTree r = new RangeTree(nodes);
        PrintWriter pw = new PrintWriter(System.out);
        int k = f.nextInt();
        X_tree x = r.x_make_tree(nodes, 0, n);
        for (int i = 0; i < k; i++) {
            r.x_find(x, f.nextDouble(),
                    f.nextDouble(), f.nextDouble(), f.nextDouble());
            if (r.f.size() == 0) {
                pw.print("None\n");
            } else {
                r.f.sort(y_sort);
                for (int j = 0; j < r.f.size(); j++) {
                    node n1 = r.f.get(j);
                    if (n1.x % 1 == 0) {
                        pw.print(n1.x.intValue() + " ");
                    } else {
                        pw.print(n1.x + " ");
                    }
                    pw.println();
                }
                for (int j = 0; j < r.f.size(); j++) {
                    node n1 = r.f.get(j);
                    if (n1.y % 1 == 0) {
                        pw.print(n1.y.intValue() + " ");
                    } else {
                        pw.print(n1.y + " ");
                    }
                }
                pw.println();
                r.f = new ArrayList<>();
            }
        }
        pw.flush();

    }
}
