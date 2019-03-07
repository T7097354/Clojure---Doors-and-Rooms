(ns matcher-starter.core
  (:require [org.clojars.cognesence.breadth-search.core :refer :all]
            [org.clojars.cognesence.matcher.core :refer :all]
            [org.clojars.cognesence.ops-search.core :refer :all]))

(def ops
  '{move {:pre ((isa ?agent agent)
                 (at ?agent ?location)
                 (connects ?location ?door)
                 (connects ?location2 ?door)
                 (state ?door open))
          :del ((at ?agent ?location))
          :add ((at ?agent ?location2))
          :cmd [move ?agent ?location2]
          :txt (moved to ?location2 from ?location)}
    open {:pre ((isa ?agent agent)
                 (at ?agent ?location)
                 (connects ?location ?door)
                 (state ?door closed)
                 (state ?door unlocked))
          :del ((state ?door closed))
          :add ((state ?door open))
          :cmd [open ?door]
          :txt (opened ?door)}
    close {:pre ((isa ?agent agent)
                  (at ?agent ?location)
                  (connects ?location ?door)
                  (state ?door open)
                  (state ?door unlocked))
           :del ((state ?door open))
           :add ((state ?door closed))
           :cmd [close ?door]
           :txt (closed ?door)}
    unlock {:pre ((isa ?agent agent)
                   (at ?agent ?location)
                   (connects ?location ?door)
                   (holds ?agent key)
                   (state ?door locked)
                   (state ?door closed))
            :del ((state ?door locked))
            :add ((state ?door unlocked))
            :cmd [unlock ?door]
            :txt (unlocked ?door)}
    lock {:pre ((isa ?agent agent)
                 (at ?agent ?location)
                 (connects ?location ?door)
                 (holds ?agent key)
                 (state ?door unlocked)
                 (state ?door closed))
          :del ((state ?door unlocked))
          :add ((state ?door locked))
          :cmd [lock ?door]
          :txt (locked ?door)}
    pickupKey {:pre ((isa ?agent agent)
                      (at ?agent ?location)
                      (contains key ?location)
                      (holds ?agent nil))
               :del ((holds ?agent nil)
                      (contains key ?location))
               :add ((holds ?agent key))
               :cmd [collected key]
               :txt (picked up key)}
   }
)

(def world
  '#{(isa lobby location)
     (isa corridor location)
     (isa vault location)
     (isa security-room location)

     (isa lobby-corridor-door door)
     (connects lobby lobby-corridor-door)
     (connects corridor lobby-corridor-door)

     (isa corridor-vault-door door)
     (connects corridor corridor-vault-door)
     (connects vault corridor-vault-door)

     (isa lobby-security-door door)
     (connects lobby lobby-security-door)
     (connects security-room lobby-security-door)

     (isa R agent)
     })

(def state
  '#{(agent R)
     (at R lobby)
     (state lobby-corridor-door closed)
     (state lobby-corridor-door locked)
     (state lobby-security-door closed)
     (state lobby-security-door unlocked)
     (state corridor-vault-door open)
     (state corridor-vault-door unlocked)
     (contains key security-room)
     (holds ?agent nil)
     })

(def goalState
  '#{(agent R)
     (at R lobby)
     (state lobby-corridor-door closed)
     (state lobby-corridor-door locked)
     (state lobby-security-door closed)
     (state corridor-vault-door closed)
     (state corridor-vault-door locked)
     })