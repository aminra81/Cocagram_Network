package calculator;

public class Calculator {
    public static long factorial(long x) {
        long ans = 1;
        for (int i = 1; i <= x; i++)
            ans *= i;
        return ans;
    }

    public static long questionFunc(long x) {
        long ans = 0;
        while (x > 0) {
            ans += factorial(x % 10);
            x /= 10;
        }
        return ans;
    }

    public static long madFunc(long x) {
        long sum = 0;
        long ted = 0;
        while (x > 0) {
            sum += x % 10;
            ted++;
            x /= 10;
        }
        return sum / ted;
    }

    public static boolean isPrime(long x) {
        for (long i = 2; i * i <= x; i++)
            if (x % i == 0)
                return false;
        return true;
    }

    public static long lessFunc(long x) {
        for (long i = x - 1; i >= 2; i--)
            if (isPrime(i))
                return i;
        return 2;
    }

    public static long mul(long a, long b) {
        return a * b;
    }

    public static long sum(long a, long b) {
        return a + b;
    }

    public static long divide(long a, long b) {
        return a / b;
    }

    public static long dollar(long a, long b) {
        StringBuilder sb = new StringBuilder();
        while (a > 0 || b > 0) {
            long digit = ((a % 10) + (b % 10)) / 2;
            sb.insert(0, (char) (digit + '0'));
            a /= 10;
            b /= 10;
        }
        long ans = 0;
        for (int i = 0; i < sb.length(); i++)
            ans = ans * 10 + (sb.charAt(i) - '0');
        return ans;
    }

    public static long solve_tak(StringBuilder sb) {
        int idx = sb.indexOf("?");
        if (idx != -1) { //processing string when it has ?
            int pos = idx - 1;
            while (pos >= 0 && '0' <= sb.charAt(pos) && sb.charAt(pos) <= '9')
                pos--;
            pos++;
            long value = questionFunc(Long.parseLong(sb.substring(pos, idx)));
            sb.delete(pos, idx + 1);
            sb.insert(pos, value);
            return solve_tak(sb);
        }
        for (int i = 0; i < sb.length(); i++)
            if (sb.charAt(i) == '<' || sb.charAt(i) == '~')
                idx = i;
        if (idx != -1) {
            int pos = idx + 1;
            while (pos < sb.length() && '0' <= sb.charAt(pos) && sb.charAt(pos) <= '9')
                pos++;
            pos--;
            long value = Long.parseLong(sb.substring(idx + 1, pos + 1));
            if (sb.charAt(idx) == '<')
                value = lessFunc(value);
            else
                value = madFunc(value);
            sb.delete(idx, pos + 1);
            sb.insert(idx, value);
            return solve_tak(sb);
        }
        ///////////////////////////////////
        for (int i = 0; i < sb.length(); i++)
            if (sb.charAt(i) == '$')
                idx = i;
        if (idx == -1)
            for (int i = 0; i < sb.length(); i++)
                if (sb.charAt(i) == '*')
                    idx = i;
        if (idx == -1)
            for (int i = 0; i < sb.length(); i++)
                if (sb.charAt(i) == '#')
                    idx = i;
        if (idx == -1)
            for (int i = 0; i < sb.length(); i++)
                if (sb.charAt(i) == '+')
                    idx = i;
        ///////////////////////////////////
        if (idx != -1) {
            ///////////////////////
            int posNext = idx + 1;
            while (posNext < sb.length() && '0' <= sb.charAt(posNext) && sb.charAt(posNext) <= '9')
                posNext++;
            posNext--;
            int posPre = idx - 1;
            while (posPre >= 0 && '0' <= sb.charAt(posPre) && sb.charAt(posPre) <= '9')
                posPre--;
            posPre++;
            ///////////////////////
            long valFirst = Long.parseLong(sb.substring(posPre, idx));
            long valSecond = Long.parseLong(sb.substring(idx + 1, posNext + 1));
            long ans = 0;
            switch (sb.charAt(idx)) {
                case '*':
                    ans = mul(valFirst, valSecond);
                    break;
                case '+':
                    ans = sum(valFirst, valSecond);
                    break;
                case '/':
                    ans = divide(valFirst, valSecond);
                    break;
                case '$':
                    ans = dollar(valFirst, valSecond);
                    break;
            }
            sb.delete(posPre, posNext + 1);
            sb.insert(posPre, ans);
            return solve_tak(sb);
        }
        return Long.parseLong(sb.toString());
    }

    public static Long solve(StringBuilder sb) {
        try {
            int idx = -1;
            int depth = 0;
            int maxDepth = 0;
            for (int i = 0; i < sb.length(); i++) {
                if (sb.charAt(i) == '(')
                    depth++;
                else if (sb.charAt(i) == ')')
                    depth--;
                if (depth > maxDepth) {
                    maxDepth = depth;
                    idx = i;
                }
            }
            if (idx == -1)
                return solve_tak(sb);
            int idxClose = -1;
            for (int i = idx + 1; i < sb.length(); i++)
                if (sb.charAt(i) == ')') {
                    idxClose = i;
                    break;
                }
            Long value = solve(new StringBuilder(sb.substring(idx + 1, idxClose)));
            sb.delete(idx, idxClose + 1);
            sb.insert(idx, value);
            return solve(sb);
        } catch (Exception e) {
            return null;
        }
    }
}
