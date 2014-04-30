package proj4;

import java.util.Arrays;

/**
 *  A generic hash table class that maps keys to values. Uses the built in hashCode method on the keys. Resizes using quadratic probing.
 *
 * @author Mark Judy
 * @version 1.0 Date: 4/23/14 Time: 8:54 PM
 */
public class Hashtable<K, V>
{
    static final int DEFAULT_SIZE = 5;
    private int entries;
    private int collisions;
    private Node<K, V>[] table;

    /**
     * Default constructor for the hashtable class.
     */
    public Hashtable()
    {
        this(DEFAULT_SIZE);
    }

    /**
     * Constructor for the hashtable class that accepts a starting size.
     *
     * @param size initial size of the hash table.
     */
    public Hashtable(int size)
    {
        entries = 0;
        collisions = 0;
        table = new Node[size];
    }

    /**
     * Returns true if the key is contained in the table, otherwise it returns false.
     *
     * @param key the key to search for in the table
     * @return true if the key is in the table, otherwise false
     */
    public boolean containsKey(K key)
    {
        return table[getPosition(key)] != null;
    }

    /**
     * Gets the value data stored at the location of the key, if the key exists in the table.
     *
     * @param key the key to search for in the table
     * @return the data associated with the search key
     */
    public V get(K key)
    {
        if(table[getPosition(key)] != null)
        {
            return table[getPosition(key)].data;
        }
        else
            return null;
    }

    /**
     * Returns the number of collisions that occurred during construction of the hash table.
     *
     * @return number of collisions
     */
    public int numCollisions()
    {
        return collisions;
    }

    /**
     * Returns the number of entries in the hash table.
     *
     * @return number of entries
     */
    public int numEntries()
    {
        return entries;
    }

    /**
     * Returns the current size of the table.
     *
     * @return size of the array containing the table
     */
    public int numSlots()
    {
        return table.length;
    }

    /**
     * Stores a new key in the table. If the table contains the key with different data, the data is overwritten.
     *
     * @param key the value to which the table maps the data
     * @param data the data to be stored in the table
     */
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
        else
        if(table[position] == null)
        {
            table[position] = new Node<K, V> (key, data);
            entries++;
        }
    }

    /**
     * Assigns a hash of the given key based on the table length and prevents location values from being negative numbers.
     *
     * @param key the key to generate a table location for
     * @return the true location of the key in the table
     */
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

    /**
     * Finds the position of a given key in the hash table.
     *
     * @param key the key to find the position of
     * @return the location of the key in the hash table
     */
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

    /**
     * Gets the position of the key in the hash table during inserts.
     *
     * @param key the key to get the position of
     * @return the position of the key in the hash table
     */
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

    /**
     * Resizes the table if the table is 80% full.
     */
    private void resize()
    {
       table = Arrays.copyOf(table, nextPrime(table.length * 2));
    }

    /**
     * Finds the next prime number for resizing the table.
     *
     * @param n The number to start searching for primes from.
     * @return New prime number size of the table.
     */
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

    /**
     * Determines if a number is prime to help the nextPrime method.
     *
     * @param n the number to check for being prime
     * @return true if the number is prime, false otherwise
     */
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

    /**
     * Helper class that holds table nodes consisting of key value pairs.
     *
     * @param <K> The identifying information associated with the data
     * @param <V> The data to be stored in the node
     */
    private class Node<K, V>
    {
        private K key;
        private V data;

        /**
         * Constructor for the helper node.
         *
         * @param k the identifying information associated with the data
         * @param v the data to be stored in the node
         */
        Node (K k, V v)
        {
            key = k;
            data = v;
        }
    }
}
