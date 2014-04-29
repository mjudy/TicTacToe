package proj4;

import java.util.Arrays;

/**
 * @author theghv
 * @version 1.0 Date: 4/23/14 Time: 8:54 PM
 */
public class Hashtable<K, V>
{
    static final int DEFAULT_SIZE = 5;
    private int entries;
    private int collisions;
    private Node<K, V>[] table;

    public Hashtable()
    {
        this(DEFAULT_SIZE);
    }

    public Hashtable(int size)
    {
        entries = 0;
        collisions = 0;
        table = new Node[size];
    }

    public boolean containsKey(K key)
    {
        if(table[getPosition(key)] != null)
        {
            return true;
        }
        else
        {
            return false;
        }

//        for(int i = 0; i < table.length; i++)
//        {
//            if(table[i] == key)
//            {
//                return true;
//            }
//        }
//        return false;
    }

    public V get(K key)
    {
        if(table[getPosition(key)] != null)
        {
            return table[getPosition(key)].data;
        }
        else
            return null;
    }

    public int numCollisions()
    {
        return collisions;
    }

    public int numEntries()
    {
        return entries;
    }

    public int numSlots()
    {
        return table.length;
    }

    public void put(K key, V data)
    {
        int position = getPositionInHashtable(key);

        if(entries > ((float)table.length * 0.8))
        {
            resize();
        }

        if(containsKey(key))
        {
            table[position].data = data;
        }
        else if(table[position] == null)
        {
            table[position] = new Node<K, V> (key, data);
            entries++;
        }
    }

    private int trueHash(K key)
    {
        int hash = key.hashCode();
        hash %= table.length;

        if (hash < 0)
        {
            hash -= table.length;
        }
        return hash;
    }

    private int getPosition(K key)
    {
        int offset = 1;
        int position = trueHash(key);

        if(table[position] != null && table[position].key.equals(key))
        {
            while (table[position] != null && table[position].key.equals(key))
            {
                position += offset;
                offset += 2;
                if(position >= table.length)
                {
                    position -= table.length;
                }
            }
        }

        return position;
    }

    private int getPositionInHashtable(K key)
    {
        int offset = 1;
        int position = trueHash(key);

        if(table[position] != null && table[position].key.equals(key))
        {
            while (table[position] != null && table[position].key.equals(key))
            {
                position += offset;
                offset += 2;
                collisions++;
                if(position >= table.length)
                {
                    position -= table.length;
                }
            }
        }

        return position;
    }

    private void resize()
    {
       table = Arrays.copyOf(table, nextPrime(table.length * 2));
    }

    private int nextPrime(int n)
    {
        if( n % 2 == 0 )
            n++;

        while(!isPrime(n))
        {
            n += 2;
        }

        return n;
    }

    private boolean isPrime(int n)
    {
        if( n == 2 || n == 3 )
            return true;

        if( n == 1 || n % 2 == 0 )
            return false;

        for( int i = 3; i * i <= n; i += 2 )
            if( n % i == 0 )
                return false;

        return true;
    }

    private class Node<K, V>
    {
        private K key;
        private V data;

        Node (K k, V v)
        {
            key = k;
            data = v;
        }

        public String toString()
        {
            return key.toString();
        }
    }
}
